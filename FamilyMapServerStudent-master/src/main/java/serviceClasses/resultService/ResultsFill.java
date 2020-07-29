package serviceClasses.resultService;

import com.google.gson.Gson;
import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoEvent;
import dataAccessClasses.DaoPerson;
import dataAccessClasses.DaoUser;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import handlerClasses.EncoderDecoder;
import handlerClasses.Handler;
import modelClasses.ModelEvent;
import modelClasses.ModelPerson;
import modelClasses.ModelUser;


import java.io.FileReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Response Body for Fill
 */
public class ResultsFill {

    private Integer currentYear = 2020;

    private EncoderDecoder coder;
    private DaoEvent eventDao;
    private DaoUser userDao;
    private DaoPerson personDao;

    final String FEMALE_NAME_FILE_PATH = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/fnames.json";
    final String MALE_NAME_FILE_PATH = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/mnames.json";
    final String LAST_NAME_FILE_PATH = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/snames.json";
    final String LOCATIONS = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/locations.json";

    private Boolean success;
    private String message;

    public ResultsFill(DatabaseDatabase database) {
        coder = new EncoderDecoder();
        eventDao = database.getEventsDao();
        userDao = database.getUserDao();
        personDao = database.getPersonDao();

        setSuccess(false);
        setMessage("Fail");

    }

    public void fillResult( String username, Integer genNum ) {

        try { ModelUser userObj = userDao.findUserByUserName(username);

            eventDao.removeEventById(userObj.getPersonId());
            personDao.removePersonById(userObj.getPersonId());

            ModelPerson personObj = (ModelPerson) coder.decode(coder.encode(userObj));

            createBirth(personObj, 0);

            ArrayList<ModelPerson> useList = new ArrayList<>();
            ArrayList<ModelPerson> nextList = new ArrayList<>();
            nextList.add((ModelPerson) coder.decode(coder.encode(userObj)));

            int num_people = 0;
            for(int i = 1; i <= genNum; i++){
                useList.clear();
                useList.addAll(nextList);

                nextList.clear();
                for(ModelPerson obj: useList){
                    ArrayList<ModelPerson> parents = createParents(username, obj, i);
                    obj.setFatherId(parents.get(0).getPersonId());
                    obj.setMotherId(parents.get(1).getPersonId());
                    try {
                        personDao.insert(obj);
                        num_people++;
                    }catch(DatabaseException e){
                        setSuccess(false);
                        setMessage("Did not add person in fill to database");
                        return;
                    }

                    nextList.addAll(parents);

                }

            }
            for(ModelPerson obj: nextList){
                try{

                    personDao.insert(obj); //add the last generation
                }catch(DatabaseException ex) {
                    setSuccess(false);
                    setMessage("Did not add person in fill to database");
                    return;
                }
                num_people++;
            }

            setSuccess(true);
            setMessage("Added " + num_people + "people & ?? num of events to database" );


        } catch (DatabaseException e) {
            this.success = false;
            this.message = "Cant find user in database";
            e.printStackTrace();
        }
    }






    public ModelPerson createPerson(String username, String lastName, String gender, Integer gen){
        String firstName;
        if(gender.equals("m")){
            firstName = returnNameFromFile(MALE_NAME_FILE_PATH);
        }
        else{
            firstName = returnNameFromFile(FEMALE_NAME_FILE_PATH);
        }

        ModelPerson personObj = new ModelPerson(
                UUID.randomUUID().toString().replace("-","").substring(0, 8),
                username,
                firstName,
                lastName,
                gender,
                null,
                null,
                null);
        //create birth event
        createBirth(personObj, gen);
        return personObj;
    }

    public ArrayList<ModelPerson> createParents(String username, ModelPerson child, Integer gen) throws DatabaseException { //passes in the name of the user
        ArrayList<ModelPerson> parents = new ArrayList<>();
        ModelPerson father = createPerson(username,child.getLastName(),"m", gen);
        ModelPerson mother = createPerson(username,child.getLastName(),"f", gen);
        father.setSpouseId(mother.getPersonId());
        mother.setSpouseId(father.getPersonId());
        createMarriage(father, mother);

        parents.add(father);
        parents.add(mother);

        return parents;
    }

    public void createBirth(ModelPerson personObj, Integer gen){
        int around_year_of_birth = currentYear - 25 - (gen*20);
        int rand_year_of_birth = (around_year_of_birth - 5) + (int)(Math.random() * (((around_year_of_birth + 5) - (around_year_of_birth - 5)) + 1));

        ModelEvent birth = (ModelEvent) coder.decode(coder.encode(returnObjectFromFile(LOCATIONS)));
        birth.setEventId(UUID.randomUUID().toString().replace("-","").substring(0, 8));
        birth.setPersonId(personObj.getPersonId());
        birth.setAssociatedUserName(personObj.getAssociatedUserName());
        birth.setEventType("birth");
        birth.setYear(rand_year_of_birth);

        try {
            this.eventDao.insert(birth);
        }catch(DatabaseException ex){
            ex.printStackTrace();
        }
        if(birth.getYear() < currentYear - 92){
            createDeath(birth);
        }
    }

    public void createDeath(ModelEvent birth){
        int around_year_of_death = birth.getYear() + 75;
        int rand_year_of_death = (around_year_of_death - 10) + (int)(Math.random() * (((around_year_of_death + 10) - (around_year_of_death - 10)) + 1));

        ModelEvent death = (ModelEvent) coder.decode(coder.encode(returnObjectFromFile(LOCATIONS)));
        death.setEventId(UUID.randomUUID().toString().replace("-","").substring(0, 8));
        death.setPersonId(birth.getPersonId());
        death.setAssociatedUserName(birth.getAssociatedUserName());
        death.setEventType("death");
        death.setYear(rand_year_of_death);
        try {
            eventDao.insert(death);
        }catch(DatabaseException ex){
            ex.printStackTrace();
        }
    }

    public void createMarriage(ModelPerson father, ModelPerson mother) throws DatabaseException {
        Integer birthYear = 0;
        for(ModelEvent e:eventDao.findEventsByAssociatedUserName(mother.getAssociatedUserName()) ){
            if(e.getEventType().equals("birth")){
                birthYear = e.getYear();
                break;
            }
        }

        int year_of_marriage = birthYear + 18;
        Object obj = returnObjectFromFile(LOCATIONS);
        ModelEvent marriageMother = (ModelEvent) coder.decode((String) obj);
        marriageMother.setEventId(UUID.randomUUID().toString().replace("-","").substring(0, 8));
        marriageMother.setPersonId(mother.getPersonId());
        marriageMother.setAssociatedUserName(mother.getAssociatedUserName());
        marriageMother.setEventType("mariage");
        marriageMother.setYear(year_of_marriage);

        ModelEvent marriageFather = (ModelEvent) coder.decode((String) obj);
        marriageFather.setEventId(UUID.randomUUID().toString().replace("-","").substring(0, 8));
        marriageFather.setPersonId(father.getPersonId());
        marriageFather.setAssociatedUserName(father.getAssociatedUserName());
        marriageFather.setEventType("mariage");
        marriageFather.setYear(year_of_marriage);

        eventDao.insert(marriageMother);
        eventDao.insert(marriageFather);
    }

    public JSONObject returnObjectFromFile(String filename){
        JSONObject object = new JSONObject();
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filename));
            object = (JSONObject) obj;
            JSONArray data = (JSONArray) object.get("data");
            int index = ThreadLocalRandom.current().nextInt(0, data.size());
            return (JSONObject) data.get(index);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return object;
    }

    public String returnNameFromFile(String filename){
        try {
            Gson gson = new Gson();
            NamesData data = gson.fromJson(new FileReader(filename), NamesData.class);
            int index = ThreadLocalRandom.current().nextInt(0, data.data.length);
            return data.data[index];
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return("");
    }

    private class NamesData{
        public String[] data;
    }


    /**
     * Sets the success variable
     * @param success Indicates if the Response Body was a success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * Sets the message variable to describe the success or fail
     * @param message Description of success or fail
     */
    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * Gets the success variable
     * @return success
     */
    public Boolean getSuccess() {
        return this.success;
    }

    /**
     * Gets the message variable
     * @return message
     */
    public String getMessage() {
        return this.message;
    }
}