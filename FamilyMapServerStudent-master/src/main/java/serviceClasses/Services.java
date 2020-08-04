package serviceClasses;

import com.google.gson.Gson;
import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoEvent;
import dataAccessClasses.DaoPerson;
import dataAccessClasses.DaoUser;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import handlerClasses.EncoderDecoder;
import modelClasses.ModelAuthToken;
import modelClasses.ModelEvent;
import modelClasses.ModelPerson;
import modelClasses.ModelUser;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Services {

    private EncoderDecoder coder = new EncoderDecoder();
    public DatabaseDatabase database = new DatabaseDatabase();
    private Connection conn = database.openConnection();

    public static String getRandomId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    public ResponseRegister registerResponse(RequestRegister register){
        ResponseRegister response = new ResponseRegister(database);
        response.setAuthToken(Services.getRandomId());
        response.setPersonId(Services.getRandomId());

        ModelUser user = coder.decodeToModelUser(coder.encode(response));
        user.setPersonID(response.getPersonId());

        response.setUserName(user.getUserName());

        ModelAuthToken authToken = new ModelAuthToken(
                response.getAuthToken(),
                user.getUserName(),
                user.getPassword()
        );
        DaoUser userDao = new DaoUser();
        DaoAuthToken tokenDao = new DaoAuthToken();
        try{
            userDao.insert(user, conn);
            tokenDao.insert(authToken, conn);
            response.setMessage("added to database in ResultsRegister service");
            response.setSuccess(true);
        } catch (SQLException e) {
            response.setMessage("Could not add to database in ResultsRegister service");
            response.setSuccess(false);
            e.printStackTrace();
        }
        fillResponse(user.getUserName(),3);
        response.setUserName(user.getUserName());
        return response;
    }

    public ResponseFill fillResponse( String username, Integer genNum ) {

        ResponseFill result = new ResponseFill(database);
        DaoUser userDao = new DaoUser();
        try{
            ModelUser userObj = userDao.getUserByUserName(username,conn);
            if(userObj.getUserName() == null){
                result.setSuccess(false);
                result.setMessage("Cant find user in database");
                return result;
            }
            DaoEvent eventDao = new DaoEvent();
            DaoPerson personDao = new DaoPerson();

            eventDao.removeEventById(userObj.getPersonID(),conn);
            personDao.removePersonById(userObj.getPersonID(),conn);

            ModelPerson personObj = coder.decodeToModelPerson(coder.encode(userObj));
            personObj.setAssociatedUsername(username);
            createBirth(personObj, 0);

            ArrayList<ModelPerson> useList = new ArrayList<>();
            ArrayList<ModelPerson> nextList = new ArrayList<>();
            nextList.add(personObj);

            int num_people = 0;
            for(int i = 1; i <= genNum; i++){
                useList.clear();
                useList.addAll(nextList);

                nextList.clear();
                for(ModelPerson obj: useList){
                    ArrayList<ModelPerson> parents = createParents(username, obj, i);
                    obj.setFatherID(parents.get(0).getPersonID());
                    obj.setMotherID(parents.get(1).getPersonID());
                    try {
                        personDao.insert(obj,conn);
                        num_people++;
                    }catch(DatabaseException e){
                        result.setSuccess(false);
                        result.setMessage("Did not add person in fill to database");
                        return result;
                    }
                    nextList.addAll(parents);
                }

            }
            for(ModelPerson obj: nextList){
                try{
                    personDao.insert(obj,conn); //add the last generation
                }catch(DatabaseException ex) {
                    result.setSuccess(false);
                    result.setMessage("Did not add person in fill to database");
                    return result;
                }
                num_people++;
            }

            result.setSuccess(true);
            result.setMessage("Added " + num_people + "people & ?? num of events to database" );
            return result;


        } catch (NullPointerException | DatabaseException e) {
            result.setSuccess(false);
            result.setMessage("Error Database");
            e.printStackTrace();
            return  result;
        }
    }


    private Integer currentYear = 2020;
    final String FEMALE_NAME_FILE_PATH = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/fnames.json";
    final String MALE_NAME_FILE_PATH = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/mnames.json";
    final String LAST_NAME_FILE_PATH = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/snames.json";
    final String LOCATIONS = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/locations.json";



    public ModelPerson createPerson(String username, String lastName, String gender, Integer gen){
        String firstName;
        if(gender.equals("m")){
            firstName = returnNameFromFile(MALE_NAME_FILE_PATH);
        }
        else{
            firstName = returnNameFromFile(FEMALE_NAME_FILE_PATH);
        }

        ModelPerson personObj = new ModelPerson(
                Services.getRandomId(),
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
        father.setSpouseID(mother.getPersonID());
        mother.setSpouseID(father.getPersonID());
        createMarriage(father, mother);

        parents.add(father);
        parents.add(mother);

        return parents;
    }

    public void createBirth(ModelPerson personObj, Integer gen){
        int around_year_of_birth = currentYear - 25 - (gen*20);
        int rand_year_of_birth = (around_year_of_birth - 5) + (int)(Math.random() * (((around_year_of_birth + 5) - (around_year_of_birth - 5)) + 1));

        ModelEvent birth = coder.decodeToModelEvent(coder.encode(returnObjectFromFile(LOCATIONS)));
        birth.setEventID(Services.getRandomId());
        birth.setPersonID(personObj.getPersonID());
        birth.setAssociatedUserName(personObj.getAssociatedUsername());
        birth.setEventType("birth");
        birth.setYear(rand_year_of_birth);
        DaoEvent eventDao = new DaoEvent();
        try {
            eventDao.insert(birth, conn);
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

        ModelEvent death = coder.decodeToModelEvent(coder.encode(returnObjectFromFile(LOCATIONS)));
        death.setEventID(UUID.randomUUID().toString().replace("-","").substring(0, 8));
        death.setPersonID(birth.getPersonID());
        death.setAssociatedUserName(birth.getAssociatedUsername());
        death.setEventType("death");
        death.setYear(rand_year_of_death);
        DaoEvent eventDao = new DaoEvent();
        try {
            eventDao.insert(death,conn);
        }catch(DatabaseException ex){
            ex.printStackTrace();
        }
    }

    public void createMarriage(ModelPerson father, ModelPerson mother) throws DatabaseException {
        Integer birthYear = 0;
        DaoEvent eventDao = new DaoEvent();
        for(ModelEvent e:eventDao.findEventsByAssociatedUserName(mother.getAssociatedUsername(),conn)){
            if(e.getEventType().equals("birth")){
                birthYear = e.getYear();
                break;
            }
        }

        int year_of_marriage = birthYear + 18;
        Object obj = returnObjectFromFile(LOCATIONS);
        ModelEvent marriageMother = coder.decodeToModelEvent(coder.encode(obj));
        marriageMother.setEventID(Services.getRandomId());
        marriageMother.setPersonID(mother.getPersonID());
        marriageMother.setAssociatedUserName(mother.getAssociatedUsername());
        marriageMother.setEventType("mariage");
        marriageMother.setYear(year_of_marriage);

        ModelEvent marriageFather = coder.decodeToModelEvent(coder.encode(obj));
        marriageFather.setEventID(Services.getRandomId());
        marriageFather.setPersonID(father.getPersonID());
        marriageFather.setAssociatedUserName(father.getAssociatedUsername());
        marriageFather.setEventType("mariage");
        marriageFather.setYear(year_of_marriage);

        eventDao.insert(marriageMother,conn);
        eventDao.insert(marriageFather,conn);
    }

    public JSONObject returnObjectFromFile(String filename){
        JSONObject object = new JSONObject();
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filename));
            object = (JSONObject) obj;
            JSONArray data = (JSONArray) object.get("data");
            int index = ThreadLocalRandom.current().nextInt(0, data.length());
            return (JSONObject) data.get(index);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return object;
    }

    public String returnNameFromFile(String filename){
        try {
            Gson gson = new Gson();
            ResponseFill.NamesData data = gson.fromJson(new FileReader(filename), ResponseFill.NamesData.class);
            int index = ThreadLocalRandom.current().nextInt(0, data.data.length);
            return data.data[index];
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return("");
    }

    public class NamesData{
        public String[] data;
    }

}
