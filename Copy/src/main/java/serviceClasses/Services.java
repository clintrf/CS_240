
package main.java.serviceClasses;

import com.google.gson.Gson;
import main.java.dataAccessClasses.*;
import main.java.modelClasses.ModelAuthTokens;
import main.java.modelClasses.ModelEvents;
import main.java.modelClasses.ModelPersons;
import main.java.modelClasses.ModelUsers;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.*;
import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;


import java.lang.*;

import main.java.dataAccessClasses.DaoEvent;


public class Services {
    private EncoderDecoder coder = new EncoderDecoder();
    private Database database = new Database();
    Connection conn = database.openConnection();


    public ResponseRegister register(RequestRegister request) throws SQLException { //fill 4 here for the user?
        ResponseRegister response = new ResponseRegister();
        DaoUser userAccess = new DaoUser();
        DaoAuthToken tokenAccess = new DaoAuthToken();

        ModelUsers userObject = coder.decodetoModelUser(coder.encodeRegister(request));

        userObject.setPersonID(getRandomIDNum());
        ModelAuthTokens authTokenObject = new ModelAuthTokens(
                getRandomIDNum(),
                userObject.getUserName(),
                userObject.getPassword()
        );


        try{
            userAccess.insert(userObject, conn);
            tokenAccess.insert(authTokenObject, conn);
            response.setMessage("added to database in ResultsRegister service");
            response.setSuccess(true);
        }catch(SQLException ex){
            response.setUserName(null);
            response.setPersonID(null);
            response.setAuthToken(null);
            response.setSuccess(false);
            response.setMessage("error");
            return response;
        }

        fill(userObject.getUserName(),4);

        response.setAuthToken(authTokenObject.getAuthToken());
        response.setUserName(userObject.getUserName());
        response.setPersonID(userObject.getPersonID());
        return response;
    }
    public ResponseLogin login(RequestLogin request) {
        DaoAuthToken authTokenDao = new DaoAuthToken();
        DaoUser userDao = new DaoUser();
        ResponseLogin response = new ResponseLogin();
        ModelUsers userObject;

        try {
            userObject = userDao.getUserByUsername(request.getUserName(), conn);
            if (!(userObject.getPassword().equals(request.getPassword()))) {
                response.setMessage("error");
                response.setSuccess(false);
                return response;
            }
        } catch (SQLException ex) {
            response.setMessage("error");
            response.setSuccess(false);
            return response;
        }

        try {
            ModelAuthTokens tokenObj = new ModelAuthTokens(
                    getRandomIDNum(),
                    userObject.getUserName(),
                    userObject.getPassword()
            );
            authTokenDao.insert(tokenObj, conn);
            response.setPersonID(userObject.getPersonID());
            response.setUserName(userObject.getUserName());
            response.setAuthToken(tokenObj.getAuthToken());
            response.setSuccess(true);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setSuccess(false);
            return response;
        }

    }

    public ResponseClear clear(){
        ResponseClear response = new ResponseClear();
        DaoAuthToken tokenAccess = new DaoAuthToken();
        DaoUser userAccess = new DaoUser();
        DaoEvent eventAccess = new DaoEvent();
        DaoPerson personAccess = new DaoPerson();

        tokenAccess.drop(conn);
        userAccess.drop(conn);
        eventAccess.drop(conn);
        personAccess.drop(conn);

        tokenAccess.create(conn);
        userAccess.create(conn);
        eventAccess.create(conn);
        personAccess.create(conn);


        String message = "Clear succeeded.";
        response.setMessage(message);

        return response;
    }

    public ResponseFill fill(String username, int generations) throws SQLException {
        DaoEvent eventsAccess = new DaoEvent();
        DaoPerson personAccess = new DaoPerson();
        DaoUser userAccess = new DaoUser();
        ResponseFill response = new ResponseFill();
        ModelUsers user;
        try {
            user = userAccess.getUserByUsername(username, conn);
        }catch(SQLException ex){
            response.setMessage("error");
            return response;
        }

        eventsAccess.removeEventByAssociatedUsername(username, conn);
        personAccess.removePersonByAssociatedUsername(username, conn);
        events_added = 0;


        String convert = coder.encodeModelUsers(user);
        ModelPersons userPerson = coder.decodetoModelPersons(convert);
        userPerson.setAssociatedUsername(username);
        createBirthDeath(userPerson, null, conn);

        ArrayList<ModelPersons> useList = new ArrayList<>();
        ArrayList<ModelPersons> nextList = new ArrayList<>();

        nextList.add(userPerson);

        int num_people = 0;
        for(int i = 0; i < generations; i++){
            useList.clear();
            for(ModelPersons p: nextList){
                useList.add(p);
            }
            nextList.clear();
            for(ModelPersons obj: useList){
                ArrayList<ModelPersons> parents = createCouple(obj, username, conn);
                obj.setFatherID(parents.get(0).getPersonID());
                obj.setMotherID(parents.get(1).getPersonID());
                try {
                    personAccess.insert(obj, conn);
                    num_people++;
                }catch(SQLException ex){
                    String message = "Could not add person in fill to database.";
                    response.setMessage(message);
                    return response;
                }

                for(ModelPersons obj2: parents){
                    nextList.add(obj2);
                }

            }

        }
        for(ModelPersons obj: nextList){
            try{
                personAccess.insert(obj, conn);
            }catch(SQLException ex) {
                String message = "Could not add person in fill to database.";
                response.setMessage(message);
                return response;
            }
            num_people++;
        }

        response.setMessage("Successfully added " + num_people + " persons and " + events_added +  " events to the database.");
        return response;
    }

    int events_added;

    final String FEMALE_NAME_FILE_PATH = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/fnames.json";
    final String MALE_NAME_FILE_PATH = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/mnames.json";
    final String LAST_NAME_FILE_PATH = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/snames.json";
    final String LOCATIONS = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/locations.json";

    public ArrayList<ModelPersons> createCouple(ModelPersons child, String associatedUsername, Connection conn ) throws SQLException { //passes in the name of the user
        ArrayList<ModelPersons> parents = new ArrayList<>();
        ModelPersons father = new ModelPersons(
                getRandomIDNum(),
                associatedUsername,
                returnNameFromFile(MALE_NAME_FILE_PATH),
                child.getLastName(),
                "m",
                null,
                null,
                null);
        ModelPersons mother = new ModelPersons(
                getRandomIDNum(),
                associatedUsername,
                returnNameFromFile(FEMALE_NAME_FILE_PATH),
                returnNameFromFile(LAST_NAME_FILE_PATH),
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

    public void createBirthDeath(ModelPersons person, ModelPersons child, Connection conn) throws SQLException {
        DaoEvent eventDao = new DaoEvent();

        ModelEvents birth = coder.decodeModelEvents(returnObjectFromFile(LOCATIONS));
        birth.setEventID(getRandomIDNum());
        birth.setAssociatedUsername(person.getAssociatedUsername());
        birth.setPersonID(person.getPersonID());
        birth.setEventType("birth");

        if(child != null){
            ArrayList<ModelEvents> events = eventDao.getEventsByPersonID(child.getPersonID() ,conn);
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

        eventDao.insert(birth, conn);
        events_added++;

        if(birth.getYear() < 2020 - 30) {
            ModelEvents death = coder.decodeModelEvents(returnObjectFromFile(LOCATIONS));

            death.setEventID(getRandomIDNum());
            death.setAssociatedUsername(person.getAssociatedUsername());
            death.setPersonID(person.getPersonID());
            death.setEventType("death");

            death.setYear(birth.getYear() - 30 - (int)(Math.random()*50));
            eventDao.insert(death, conn);
            events_added++;
        }
    }

    public void createMarriage(ModelPersons father, ModelPersons mother, Connection conn) throws SQLException {
        DaoEvent eventDao = new DaoEvent();
        int youngestAge = 2020;
        ArrayList<ModelEvents> eventsfather = eventDao.getEventsByPersonID(father.getPersonID() ,conn);
        for(ModelEvents event: eventsfather){
            if(event.getEventType().equals("birth")) {
                youngestAge = (event).getYear() + 13;
                break;
            }
        }
        ArrayList<ModelEvents> eventsMother = eventDao.getEventsByPersonID(mother.getPersonID() ,conn);
        for(ModelEvents event: eventsMother){
            if(event.getEventType().equals("birth") &&((event).getYear() + 13 > youngestAge) ) {
                youngestAge = (event).getYear() + 13;
                break;
            }
        }
        youngestAge +=1;
        String eventID = UUID.randomUUID().toString().replace("-", "");
        String eventID2 = eventID.substring(0,8);
        String eventID3 = UUID.randomUUID().toString().replace("-", "");
        String eventID4 = eventID3.substring(0,8);

        JSONObject obj = returnObjectFromFile(LOCATIONS);
        ModelEvents marriage1 = coder.decodeModelEvents(obj);
        marriage1.setEventID(eventID2);
        marriage1.setPersonID(father.getPersonID());
        marriage1.setAssociatedUsername(father.getAssociatedUsername());
        marriage1.setEventType("marriage");
        marriage1.setYear(youngestAge);

        ModelEvents marriage2 = coder.decodeModelEvents(obj);
        marriage2.setEventID(eventID4);
        marriage2.setPersonID(mother.getPersonID());
        marriage2.setAssociatedUsername(mother.getAssociatedUsername());
        marriage2.setEventType("marriage");
        marriage2.setYear(youngestAge);

        DaoEvent eventAccess = new DaoEvent();
        eventAccess.insert(marriage1, conn);
        eventAccess.insert(marriage2, conn);
        events_added++;
        events_added++; //two for marriage
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
            Names data = gson.fromJson(new FileReader(filename), Names.class);
            int index = ThreadLocalRandom.current().nextInt(0, data.data.length);
            return data.data[index];
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return("");
    }

    private class Names{
        public String[] data;
    }




    public ResponseLoad load(RequestLoad request){
        ResponseLoad response = new ResponseLoad();

        clear();

        DaoEvent eventAccess = new DaoEvent();
        DaoUser userAccess = new DaoUser();
        DaoPerson personAccess = new DaoPerson();

        int num_persons = 0;
        int num_users = 0;
        int num_events = 0;

        for(ModelUsers user: request.getUsers()){ //should we create an auth token for these people?
            try{
                userAccess.insert(user, conn);
                num_users++;
            }catch(SQLException ex){
                String message = "error In Load: could not add person to database.";
                response.setMessage(message);
                return response;
            }
        }

        for(ModelPersons person: request.getPeople()){
            try {
                personAccess.insert(person, conn);
                num_persons++;
            }catch(SQLException ex){
                String message = "error In Load: could not add person to database.";
                response.setMessage(message);
                return response;
            }
        }

        for(ModelEvents event: request.getEvents()){
            try{
                eventAccess.insert(event, conn);
                num_events++;
            }catch(SQLException ex){
                response.setMessage("error");
                return response;
            }
        }

        String message = "Successfully added " + num_users + " users, " + num_persons + " persons, and " + num_events + " events to the database.";
        response.setMessage(message);
        response.setSuccess(true);
        return response;
    }

    public ResponsePerson person(String auth_token, String personID){

        ResponsePerson response = new ResponsePerson();
        DaoPerson personAccess = new DaoPerson();
        DaoAuthToken tokenAccess = new DaoAuthToken();
        String username;
        try {
            ModelAuthTokens tokenObject = tokenAccess.getAuthTokenByToken(auth_token, conn);
            String descendant = tokenObject.getUserName();
            ModelPersons person = personAccess.getPersonByID(personID, conn);
            if(!(person.getAssociatedUsername().equals(descendant))){
                response.setMessage("error");
                response.setSuccess(false);
                return response;
            }
        }catch(SQLException ex){
            response.setMessage("error");
            response.setSuccess(false);
            return response;
        }

        try {
            ModelPersons personObject = personAccess.getPersonByID(personID, conn);
            response = coder.decodetoResponsePerson(coder.encodeModelPersons(personObject));

            response.setMessage("Successful");
            response.setSuccess(true);
            return response;
        }catch(SQLException ex){
            response.setMessage("error");
            response.setSuccess(false);
            return response;
        }

    }

    public ResponsePeople people(String auth_token){
        ResponsePeople response = new ResponsePeople();
        DaoAuthToken tokenAccess = new DaoAuthToken();
        String username;
        try {
            ModelAuthTokens tokenObject = tokenAccess.getAuthTokenByToken(auth_token, conn);
            username = tokenObject.getUserName();
        }catch(SQLException ex){
            response.setMessage("error");
            response.setSuccess(false);
            return response;
        }

        DaoPerson personAccess = new DaoPerson();
        ArrayList<ModelPersons> data;
        try {
            data = personAccess.getPeopleByAssociatedUsername(username, conn);
        }catch(SQLException ex){
            response.setSuccess(false);
            response.setMessage("error");
            return response;
        }

        if(data.size() == 0) {
            response.setMessage("error");
            response.setSuccess(false);
            response.setData(null);
            return response;
        }

        response.setData(data);
        response.setSuccess(true);
        return response;
    }

    public ResponseEvent event(String auth_token, String eventID){
        ResponseEvent response = new ResponseEvent();
        DaoAuthToken tokenAccess = new DaoAuthToken();
        DaoEvent eventAccess = new DaoEvent();
        ModelAuthTokens tokenObject;
        try {
            tokenObject = tokenAccess.getAuthTokenByToken(auth_token, conn);
            String descendant = tokenObject.getUserName();
            ModelEvents event = eventAccess.getEventById(eventID, conn);
            if(!(event.getAssociatedUsername().equals(descendant))){
                response.setMessage("error");
                response.setSuccess(false);
                return response;
            }
        }catch(SQLException ex){
            response.setMessage("error");
            response.setSuccess(false);
            return response;
        }
        ModelEvents eventObject = new ModelEvents(null, null, null, null, null, null, null, null, null);
        try {
            eventObject = eventAccess.getEventById(eventID, conn);
            response = coder.decodetoResponseEvent(coder.encodeModelEvents(eventObject));
        }catch(SQLException ex){
            eventObject.setNull();
            response.setMessage("error");
            response.setSuccess(false);
            return response;
        }
        response.setSuccess(true);
        return response;
    }

    public ResponseEvents events(String auth_token){
        ResponseEvents response = new ResponseEvents();
        DaoAuthToken tokenAccess = new DaoAuthToken();
        String username;
        try {
            ModelAuthTokens tokenObject = tokenAccess.getAuthTokenByToken(auth_token, conn);
            username = tokenObject.getUserName();
        }catch(SQLException ex){
            response.setMessage("error");
            response.setSuccess(false);
            return response;
        }

        DaoEvent eventAccess = new DaoEvent();
        ArrayList<ModelEvents> data;
        try {
            data = eventAccess.getEventsByAssociatedUsername(username, conn);
        }catch(SQLException ex){
            String message = "error No persons related to this user.";
            response.setMessage(message);
            response.setSuccess(false);
            return response;
        }

        if(data.size() == 0) {
            response.setMessage("error");
            response.setData(null);
            response.setSuccess(false);
            return response;
        }
        response.setData(data);
        response.setSuccess(true);
        return response;

    }
    public String getRandomIDNum(){
        return UUID.randomUUID().toString().replace("-", "").substring(0,8);
    }
}