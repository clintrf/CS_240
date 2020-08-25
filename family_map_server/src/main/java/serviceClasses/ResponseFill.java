package main.java.serviceClasses;

import com.google.gson.Gson;
import main.java.daoClasses.*;
import main.java.modelClasses.ModelEvents;
import main.java.modelClasses.ModelPersons;
import main.java.modelClasses.ModelUsers;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ResponseFill{

    private String message;
    private Boolean success;

    public void setMessage(String message){ this.message = message; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getMessage() { return this.message; }
    public Boolean getSuccess() { return this.success; }



    private static int eventTotal;
    public static int getEventTotal(){ return eventTotal; }
    public static void setEventTotal(int number){ eventTotal = number; }
    public static void incrementEventTotal(){ eventTotal++; }

    public static ResponseFill fill(String username, int numGen, Connection conn) {
        EncoderDecoder coder = new EncoderDecoder();
        ResponseFill fillResponse = new ResponseFill();

        DaoEvent eventDao = new DaoEvent(conn);
        DaoPerson personDao = new DaoPerson(conn);
        DaoUser userAccess = new DaoUser(conn);

        ModelUsers userModel;
        ModelPersons personModel;
        int peopleTotal = 0;
        try {
            userModel = userAccess.getUserByUsername(username);
            if (userModel == null){
                throw new SQLException();
            }
            eventDao.removeEventByAssociatedUsername(username);
            personDao.removePersonByAssociatedUsername(username);
            setEventTotal(0);

            personModel = coder.decodeModelPersons(coder.encodeModelUsers(userModel));
            personModel.setAssociatedUsername(username);
            createBirthDeath(personModel, null, conn);

            ArrayList<ModelPersons> currentGen = new ArrayList<>();
            ArrayList<ModelPersons> nextGen = new ArrayList<>();

            nextGen.add(personModel);


            for(int i = 1; i <= numGen; i++){
                currentGen.addAll(nextGen);
                nextGen.clear();

                for(ModelPersons person: currentGen){
                    ArrayList<ModelPersons> parents = createCouple(person, username, conn);
                    person.setFatherID(parents.get(0).getPersonID());
                    person.setMotherID(parents.get(1).getPersonID());
                    personDao.insert(person);
                    peopleTotal++;

                    if(i == numGen){
                        personDao.insert(parents.get(0));
                        personDao.insert(parents.get(1));
                        peopleTotal++;
                        peopleTotal++;
                    }
                    nextGen.addAll(parents);
                }
                currentGen.clear();
            }
            fillResponse.setMessage("Successfully added " + peopleTotal + " persons and " + getEventTotal() +  " events to the database.");
            fillResponse.setSuccess(true);
            return fillResponse;
        }catch(SQLException ex){
            fillResponse.setMessage("error");
            fillResponse.setSuccess(false);
            return fillResponse;
        }
    }



    public static ArrayList<ModelPersons> createCouple(ModelPersons child, String associatedUsername, Connection conn) throws SQLException {
        final String FEMALE_NAME = "/home/clintfrandsen/GITHUB/CS_240/family_map_server/src/json/fnames.json";
        final String MALE_NAME = "/home/clintfrandsen/GITHUB/CS_240/family_map_server/src/json/mnames.json";
        final String LAST_NAME = "/home/clintfrandsen/GITHUB/CS_240/family_map_server/src/json/snames.json";
        ArrayList<ModelPersons> parents = new ArrayList<>();
        ModelPersons father = new ModelPersons(
                getRandomIDNum(),
                associatedUsername,
                nameFromFile(MALE_NAME),
                child.getLastName(),
                "m",
                null,
                null,
                null);
        ModelPersons mother = new ModelPersons(
                getRandomIDNum(),
                associatedUsername,
                nameFromFile(FEMALE_NAME),
                nameFromFile(LAST_NAME),
                "f",
                null,
                null,
                null);
        createBirthDeath(mother, child, conn);
        createBirthDeath(father, child,  conn);

        father.setSpouseID(mother.getPersonID());
        mother.setSpouseID(father.getPersonID());
        createMarriage(father, mother, conn);

        parents.add(father);
        parents.add(mother);

        return parents;
    }

    public static void createBirthDeath(ModelPersons person, ModelPersons child, Connection conn) throws SQLException {
        final String LOCATION = "/home/clintfrandsen/GITHUB/CS_240/family_map_server/src/json/locations.json";
        EncoderDecoder coder = new EncoderDecoder();
        DaoEvent eventDao = new DaoEvent(conn);

        ModelEvents birth = coder.decodeModelEvents(locationFromFile(LOCATION));
        birth.setEventID(getRandomIDNum());
        birth.setAssociatedUsername(person.getAssociatedUsername());
        birth.setPersonID(person.getPersonID());
        birth.setEventType("birth");

        if(child != null){
            ArrayList<ModelEvents> events = eventDao.getEventsByPersonID(child.getPersonID());
            for(ModelEvents event: events){
                if(event.getEventType().equals("birth")) {
                    birth.setYear((event).getYear() - 13 - (int)(Math.random()*17));
                    break;
                }
            }
        }
        else{
            birth.setYear(2020-25);
        }

        eventDao.insert(birth);
        incrementEventTotal();

        if(birth.getYear() < 2020 - 30) {
            ModelEvents death = coder.decodeModelEvents(locationFromFile(LOCATION));

            death.setEventID(getRandomIDNum());
            death.setAssociatedUsername(person.getAssociatedUsername());
            death.setPersonID(person.getPersonID());
            death.setEventType("death");

            death.setYear(birth.getYear() + 30 + (int)(Math.random()*50));
            eventDao.insert(death);
            incrementEventTotal();
        }
    }

    public static void createMarriage(ModelPersons father, ModelPersons mother, Connection conn) throws SQLException {
        final String LOCATION = "/home/clintfrandsen/GITHUB/CS_240/family_map_server/src/json/locations.json";
        EncoderDecoder coder = new EncoderDecoder();
        DaoEvent eventDao = new DaoEvent(conn);
        int youngestAge = 2020;
        ArrayList<ModelEvents> eventsfather = eventDao.getEventsByPersonID(father.getPersonID());
        for(ModelEvents event: eventsfather){
            if(event.getEventType().equals("birth")) {
                youngestAge = (event).getYear() + 13;
                break;
            }
        }
        ArrayList<ModelEvents> eventsMother = eventDao.getEventsByPersonID(mother.getPersonID());
        for(ModelEvents event: eventsMother){
            if(event.getEventType().equals("birth") &&((event).getYear() + 13 > youngestAge) ) {
                youngestAge = (event).getYear() + 13;
                break;
            }
        }
        youngestAge +=1;

        JSONObject obj = locationFromFile(LOCATION);
        ModelEvents fatherMarriage = coder.decodeModelEvents(obj);
        fatherMarriage.setEventID(getRandomIDNum());
        fatherMarriage.setPersonID(father.getPersonID());
        fatherMarriage.setAssociatedUsername(father.getAssociatedUsername());
        fatherMarriage.setEventType("marriage");
        fatherMarriage.setYear(youngestAge);

        ModelEvents motherMarriage = coder.decodeModelEvents(obj);
        motherMarriage.setEventID(getRandomIDNum());
        motherMarriage.setPersonID(mother.getPersonID());
        motherMarriage.setAssociatedUsername(mother.getAssociatedUsername());
        motherMarriage.setEventType("marriage");
        motherMarriage.setYear(youngestAge);

        DaoEvent eventAccess = new DaoEvent(conn);
        eventAccess.insert(fatherMarriage);
        eventAccess.insert(motherMarriage);
        incrementEventTotal();
        incrementEventTotal();
    }


    public static JSONObject locationFromFile(String filename){
        try{
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(filename);
            JSONObject object = (JSONObject) parser.parse(reader);
            JSONArray data = (JSONArray) object.get("data");
            return (JSONObject) data.get(ThreadLocalRandom.current().nextInt(0, data.size()));
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String nameFromFile(String filename){
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(filename);
            NameDataClass dataName = gson.fromJson(reader, NameDataClass.class);
            return dataName.data[ThreadLocalRandom.current().nextInt(0, dataName.data.length)];
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private class NameDataClass {
        public String[] data;
    }

    public static String getRandomIDNum(){
        return UUID.randomUUID().toString().replace("-", "").substring(0,8);
    }


}