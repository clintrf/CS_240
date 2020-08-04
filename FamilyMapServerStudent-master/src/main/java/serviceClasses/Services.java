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
    private DatabaseDatabase database = new DatabaseDatabase();
    private Creator create = new Creator();
    Connection conn = database.openConnection();


    public ResponseRegister register(RequestRegister request) throws SQLException { //fill 4 here for the user?
        ResponseRegister response = new ResponseRegister();

        System.out.println("IN SERVER FACADE");

        String convert = coder.encodeRegister(request);
        ModelUser userObject = coder.decodetoModelUser(convert);

        String personID = UUID.randomUUID().toString().replace("-", "");
        String auth_token = UUID.randomUUID().toString().replace("-", "");

        String personID2 = personID.substring(0, 8); //personID generated
        String auth_token2 = auth_token.substring(0, 8); //auth_token generated

        userObject.setPersonID(personID2);
        ModelAuthToken authTokenObject = new ModelAuthToken(auth_token2, userObject.getUserName(), userObject.getPassword());

        DaoUser userAccess = new DaoUser();
        DaoAuthToken tokenAccess = new DaoAuthToken();
        try {
            userAccess.insert(userObject, conn); //one of these is throwing an exception
            tokenAccess.insert(authTokenObject, conn);
            response.setMessage("added to database in ResultsRegister service");
            response.setSuccess(true);
        } catch (SQLException ex) {
            String message = "Error: Could not add to database.";
            response.setUserName(null);
            response.setPersonID(null);
            response.setAuthToken(null);
            response.setSuccess(false);
            response.setMessage(message);
            return response;
        }

        fill(userObject.getUserName());

        response.setAuthToken(authTokenObject.getAuthToken());
        response.setUserName(userObject.getUserName());
        response.setPersonID(personID2);
        response.setAuthToken(auth_token2);
        return response;
    }

    public ResponseLogin login(RequestLogin request) {
        ResponseLogin response = new ResponseLogin();
        ModelUser userObject;
        try {
            DaoUser userAccess = new DaoUser();
            userObject = userAccess.getUserByUsername(request.getUserName(), conn); //may throw exception
            if (!(userObject.getPassword().equals(request.getPassword()))) { //checks to make sure password matches
                throw new Exception();
            }
        } catch (SQLException ex) {
            String message = "error: User is not in database";
            response.setMessage(message);
            response.setSuccess(false);
            return response;
        } catch (Exception ex) {
            String message = "Error: Credentials not applicable";
            response.setMessage(message);
            response.setSuccess(false);
            return response;
        }

        DaoAuthToken accessTokens = new DaoAuthToken();
        String returnusername = userObject.getUserName();
        String returnpersonID = userObject.getPersonID();
        try {
            ModelAuthToken tokensObject = accessTokens.getAuthTokenByUsername(returnusername, conn);
        } catch (Exception ex) {
            String message = "Error: cant tink authToken by userName";
            response.setMessage(message);
            response.setSuccess(false);
            return response;
        }

        String auth = UUID.randomUUID().toString().replace("-", "");
        String auth2 = auth.substring(0, 8); //personID generated

        try {
            accessTokens.insert(new ModelAuthToken(auth2, returnusername, userObject.getPassword()), conn);
            response.setPersonID(returnpersonID);
            response.setUserName(returnusername);
            response.setAuthToken(auth2);
            response.setSuccess(true);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setSuccess(false);
            return response;
        }


    }

    public ResponseClear clear() { //how can this have an errors?
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

    public void deleteAllPertainingtoUser(String username) {

        DaoEvent eventsAccess = new DaoEvent();
        DaoPerson personAccess = new DaoPerson();

        eventsAccess.removeEventByAssociatedUsername(username, conn);
        personAccess.removePersonByAssociatedUsername(username, conn); //person is own descendant

    }

    public ResponseFill fill(String username, int generations) throws SQLException {

        ResponseFill response = new ResponseFill();
        DaoUser userAccess = new DaoUser();
        ModelUser user;
        try {
            user = userAccess.getUserByUsername(username, conn);
        } catch (SQLException ex) {
            response.setMessage("Unable to locate user in database.");
            return response;
        }

        deleteAllPertainingtoUser(username);

        create.seteventstozero(); //reset events added to zero

        String convert = coder.encodeModelUsers(user);
        ModelPerson userPerson = coder.decodetoModelPersons(convert);
        userPerson.setAssociatedUsername(username);
        create.createBirth(userPerson, username, conn); //adds own users birth

        ArrayList<ModelPerson> useList = new ArrayList<ModelPerson>();
        ArrayList<ModelPerson> nextList = new ArrayList<ModelPerson>();
        DaoPerson personAccess = new DaoPerson();
        nextList.add(userPerson);

        int num_people = 0;
        for (int i = 1; i <= generations; i++) {
            useList.clear();
            for (ModelPerson p : nextList) {
                useList.add(p);
            }
            nextList.clear();
            for (ModelPerson obj : useList) {
                ArrayList<ModelPerson> parents = create.createCouple(username, conn, i, obj);
                obj.setFatherID(parents.get(0).getPersonID()); //assign IDs to the proper child
                obj.setMotherID(parents.get(1).getPersonID());
                try {
                    personAccess.insert(obj, conn); //add the person to the database
                    num_people++;
                } catch (SQLException ex) {
                    String message = "Could not add person in fill to database.";
                    response.setMessage(message);
                    return response;
                }

                for (ModelPerson obj2 : parents) {
                    nextList.add(obj2);
                }

            }

        }
        for (ModelPerson obj : nextList) {
            try {
                personAccess.insert(obj, conn); //add the last generation
            } catch (SQLException ex) {
                String message = "Could not add person in fill to database.";
                response.setMessage(message);
                return response;
            }
            num_people++;
        }

        response.setMessage("Successfully added " + num_people + " persons and " + create.getevents() + " events to the database.");
        return response;
    }

    public ResponseFill fill(String username) throws SQLException {

        final int default_generations = 4;
        ResponseFill response = new ResponseFill();
        DaoUser userAccess = new DaoUser();
        ModelUser user;
        try {
            user = userAccess.getUserByUsername(username, conn);
        } catch (SQLException ex) {
            response.setMessage("Unable to locate user in database.");
            return response;
        }

        deleteAllPertainingtoUser(username);

        create.seteventstozero(); //reset events added to zero

        String convert = coder.encodeModelUsers(user);
        ModelPerson userPerson = coder.decodetoModelPersons(convert);
        userPerson.setAssociatedUsername(username);
        create.createBirth(userPerson, username, conn); //adds own users birth

        ArrayList<ModelPerson> useList = new ArrayList<ModelPerson>();
        ArrayList<ModelPerson> nextList = new ArrayList<ModelPerson>();
        DaoPerson personAccess = new DaoPerson();
        nextList.add(userPerson);


        int num_people = 0;
        for (int i = 1; i <= default_generations; i++) {
            useList.clear();
            for (ModelPerson p : nextList) {
                useList.add(p);
            }
            nextList.clear();
            for (ModelPerson obj : useList) {
                ArrayList<ModelPerson> parents = create.createCouple(username, conn, i, obj);
                obj.setFatherID(parents.get(0).getPersonID()); //assign IDs to the proper child
                obj.setMotherID(parents.get(1).getPersonID());
                try {
                    personAccess.insert(obj, conn); //add the person to the database
                    num_people++;
                } catch (SQLException ex) {
                    String message = "Could not add person in fill to database.";
                    response.setMessage(message);
                    return response;
                }

                for (ModelPerson obj2 : parents) {
                    nextList.add(obj2);
                }

            }

        }
        for (ModelPerson obj : nextList) {
            try {
                personAccess.insert(obj, conn); //add the last generation
            } catch (SQLException ex) {
                String message = "Could not add person in fill to database.";
                response.setMessage(message);
                return response;
            }
            num_people++;
        }

        response.setMessage("Successfully added " + num_people + " persons and " + create.getevents() + " events to the database.");
        return response;
    }

    /**
     * Takes a request Object containing request information and returns an object containing response information
     *
     * @param request a RequestLoad object containing information to complete a load request
     */
    public ResponseLoad load(RequestLoad request) {
        ResponseLoad response = new ResponseLoad();

        clear();

        DaoEvent eventAccess = new DaoEvent();
        DaoUser userAccess = new DaoUser();
        DaoPerson personAccess = new DaoPerson();

        int num_persons = 0;
        int num_users = 0;
        int num_events = 0;

        for (ModelUser user : request.getUsers()) { //should we create an auth token for these people?
            try {
                userAccess.insert(user, conn);
                num_users++;
            } catch (SQLException ex) {
                String message = "error In Load: could not add person to database.";
                response.setMessage(message);
                return response;
            }
        }

        for (ModelPerson person : request.getPeople()) {
            try {
//                if(person.getAssociatedUsername() == null){
//
//                    person.setAssociatedUsername(userAccess.getRowbyPersonID(person.getPersonID(),conn).getUserName());
//                }
                personAccess.insert(person, conn);
                num_persons++;
            } catch (SQLException ex) {
                String message = "error In Load: could not add person to database.";
                response.setMessage(message);
                return response;
            }
        }

        for (ModelEvent event : request.getEvents()) {
            try {
//                if(event.getAssociatedUsername() == null){
//                    event.setAssociatedUsername(personAccess.getPersonByID(event.getPersonID(),conn).getAssociatedUsername());
//                }
                eventAccess.insert(event, conn);
                num_events++;
            } catch (SQLException ex) {
                String message = "error In Load: could not add person to database.";
                response.setMessage(message);
                return response;
            }
        }

        String message = "Successfully added " + num_users + " users, " + num_persons + " persons, and " + num_events + " events to the database.";
        response.setMessage(message);
        response.setSuccess(true);
        return response;
    }

    /**
     * Retrieves a person from the server and returns an object containing response information
     *
     * @param personID passes in the eventID for the event information to return
     */
    public ResponsePerson person(String auth_token, String personID) {

        ResponsePerson response = new ResponsePerson();
        DaoPerson personAccess = new DaoPerson();
        DaoAuthToken tokenAccess = new DaoAuthToken();
        String username;
        try {
            ModelAuthToken tokenObject = tokenAccess.getAuthTokenByToken(auth_token, conn);
            String descendant = tokenObject.getUserName();
            ModelPerson person = personAccess.getPersonByID(personID, conn);
            if (!(person.getAssociatedUsername().equals(descendant))) {
                String message = "error: You are not authorized to access this.";

                response.setMessage(message);
                response.setSuccess(false);
                return response;
            }
        } catch (SQLException ex) {
            String message = "error: Invalid auth_token.";
            response.setMessage(message);
            return response;
        }

        try {
            ModelPerson personObject = personAccess.getPersonByID(personID, conn);
            response = coder.decodetoResponsePerson(coder.encodeModelPersons(personObject));

            response.setMessage("Successful");
            response.setSuccess(true);
            return response;
        } catch (SQLException ex) {
            String message = "Error:Person is not in database.";
            response.setMessage(message);
            return response;
        }

    }

    /**
     * Retrieves multiple persons from the server and returns an object containing response information
     */
    public ResponsePeople people(String auth_token) {
        ResponsePeople response = new ResponsePeople();
        DaoAuthToken tokenAccess = new DaoAuthToken();
        String username;
        try {
            ModelAuthToken tokenObject = tokenAccess.getAuthTokenByToken(auth_token, conn);
            username = tokenObject.getUserName();
        } catch (SQLException ex) {
            String message = "error Invalid auth_token.";
            response.setMessage(message);
            return response;
        }

        DaoPerson personAccess = new DaoPerson();
        ArrayList<ModelPerson> data;
        try {
            data = personAccess.getPeopleByAssociatedUsername(username, conn);
        } catch (SQLException ex) {
            String message = "error No persons related to this user.";
            response.setSuccess(false);
            response.setMessage(message);
            return response;
        }

        if (data.size() == 0) {
            String message = "error There are no persons related to this user in the database";
            response.setMessage(message);
            response.setSuccess(false);
            response.setData(null);
            return response;
        }

        response.setData(data);
        response.setSuccess(true);
        return response;
    }

    /**
     * Retrieves an event from the server and returns an object containing response information
     *
     * @param eventID passes in the eventID for the event information to return
     */
    public ResponseEvent event(String auth_token, String eventID) {
        ResponseEvent response = new ResponseEvent();
        DaoAuthToken tokenAccess = new DaoAuthToken();
        DaoEvent eventAccess = new DaoEvent();
        ModelAuthToken tokenObject;
        try {
            tokenObject = tokenAccess.getAuthTokenByToken(auth_token, conn);
            String descendant = tokenObject.getUserName();
            ModelEvent event = eventAccess.getEventById(eventID, conn); //gets the event to compare descendants
            if (!(event.getAssociatedUsername().equals(descendant))) {
                String message = "error You are not authorized to access this.";
                response.setMessage(message);
                response.setSuccess(false);
                return response;
            }
        } catch (SQLException ex) {
            String message = "error Invalid auth_token.";
            response.setMessage(message);
            response.setSuccess(false);
            return response;
        }
        ModelEvent eventObject = new ModelEvent(null, null, null, null, null, null, null, null, null);
        try {
            eventObject = eventAccess.getEventById(eventID, conn);
            response = coder.decodetoResponseEvent(coder.encodeModelEvents(eventObject));
        } catch (SQLException ex) {
            eventObject.setNull();
            String message = "error Event is not in database.";
            response.setMessage(message);
            response.setSuccess(false);
            return response;
        }
        response.setSuccess(true);
        return response;
    }

    /**
     * Retrieves multiple events from the server and returns an object containing response information
     */
    public ResponseEvents events(String auth_token) {
        ResponseEvents response = new ResponseEvents();
        DaoAuthToken tokenAccess = new DaoAuthToken();
        String username;
        try {
            ModelAuthToken tokenObject = tokenAccess.getAuthTokenByToken(auth_token, conn);
            username = tokenObject.getUserName();
        } catch (SQLException ex) {
            String message = "error Invalid auth_token.";
            response.setMessage(message);
            response.setSuccess(false);
            return response;
        }

        System.out.println("USERNAME: " + username);
//
        DaoEvent eventAccess = new DaoEvent();
        ArrayList<ModelEvent> data;
        try {
            data = eventAccess.getEventsByAssociatedUsername(username, conn);
        } catch (SQLException ex) {
            String message = "error No persons related to this user.";
            response.setMessage(message);
            response.setSuccess(false);
            return response;
        } //has a list of all people related to user at this point

        if (data.size() == 0) {
            String message = "error There are no events related to this user in the database";
            response.setMessage(message);
            response.setData(null);
            response.setSuccess(false);
            return response;
        }
        response.setData(data);
        response.setSuccess(true);
        return response;

    }
}

class Creator{

    EncoderDecoder coder = new EncoderDecoder();

    int events_added;

    final String FEMALE_NAME_FILE_PATH = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/fnames.json";
    final String MALE_NAME_FILE_PATH = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/mnames.json";
    final String LAST_NAME_FILE_PATH = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/snames.json";
    final String LOCATIONS = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/locations.json";


    int base_year = 2017;
    int gen = 0;
    int birth_year_of_one_individual = 0;

    public void seteventstozero(){
        events_added = 0;
    }

    public int getevents(){
        return events_added;
    }

    public ArrayList<ModelPerson> createCouple(String descendant, Connection conn, int generation, ModelPerson child) throws SQLException { //passes in the name of the user
        gen = generation;
        ArrayList<ModelPerson> parents = new ArrayList<ModelPerson>(); //father first, mother second
        ModelPerson father = createPerson("m", descendant, conn, child);
        ModelPerson mother = createPerson("f", descendant, conn);
        father.setSpouseID(mother.getPersonID());
        mother.setSpouseID(father.getPersonID());
        createMarriage(father, mother, conn);

        parents.add(father);
        parents.add(mother);

        return parents;
        //add to parents back in other function
    }

    public ModelPerson createPerson(String gender, String descendant, Connection conn, ModelPerson child) throws SQLException { //passes in the gender of the person to create

        String personID = UUID.randomUUID().toString().replace("-", "");
        String personID2 = personID.substring(0,8); //personID generated
        String first_name;
        first_name = returnNameFromFile(MALE_NAME_FILE_PATH);
        ModelPerson response = new ModelPerson(
                personID2,
                descendant,
                first_name,
                child.getLastName(),
                gender,
                null, //add these in other class
                null,
                null); //add in other method
        //create birth event
        createBirth(response, descendant, conn);
        return response;
    }

    public ModelPerson createPerson(String gender, String descendant, Connection conn) throws SQLException { //passes in the gender of the person to create

        String personID = UUID.randomUUID().toString().replace("-", "");
        String personID2 = personID.substring(0,8); //personID generated
        String first_name = returnNameFromFile(FEMALE_NAME_FILE_PATH);
        String last_name = returnNameFromFile(LAST_NAME_FILE_PATH);
        ModelPerson response = new ModelPerson(
                personID2,
                descendant,
                first_name,
                last_name,
                gender,
                null, //add these in other class
                null,
                null); //add in other method
        //create birth event
        createBirth(response, descendant, conn);
        return response;
    }

    public void createBirth(ModelPerson person, String descendant, Connection conn) throws SQLException {
        //pull in random city, country, long, and latitude
        String eventID = UUID.randomUUID().toString().replace("-", "");
        String eventID2 = eventID.substring(0,8);
        String personID = person.getPersonID();
        String eventType = "birth";
        int around_year_of_birth = base_year - 25 - (gen*20);
        int rand_year_of_birth = (around_year_of_birth - 5) + (int)(Math.random() * (((around_year_of_birth + 5) - (around_year_of_birth - 5)) + 1));
        birth_year_of_one_individual = rand_year_of_birth;
        ModelEvent birth = coder.changetoModelEvents(returnObjectFromFile(LOCATIONS));
        birth.setEventID(eventID2);
        birth.setPersonID(personID);
        birth.setAssociatedUsername(descendant);
        birth.setEventType(eventType);
        birth.setYear(rand_year_of_birth);
        DaoEvent eventAccess = new DaoEvent();
//            System.out.println("adding event");
        eventAccess.insert(birth, conn);

        events_added++;
        if(rand_year_of_birth < base_year - 92){
            createDeath(person, descendant, conn, rand_year_of_birth);
        }
    }

    public void createDeath(ModelPerson person, String descendant, Connection conn, int birthyear) throws SQLException { //if they are dead
        String eventID = UUID.randomUUID().toString().replace("-", "");
        String eventID2 = eventID.substring(0,8);
        String personID = person.getPersonID();
        int around_year_of_death = birthyear + 75;
        int rand_year_of_death = (around_year_of_death - 10) + (int)(Math.random() * (((around_year_of_death + 10) - (around_year_of_death - 10)) + 1));
        ModelEvent death = coder.changetoModelEvents(returnObjectFromFile(LOCATIONS));
        death.setEventID(eventID2);
        death.setPersonID(personID);
        death.setAssociatedUsername(descendant);
        death.setEventType("death");
        death.setYear(rand_year_of_death);
        DaoEvent eventAccess = new DaoEvent();
        eventAccess.insert(death, conn);
        events_added++;
    }

    public void createMarriage(ModelPerson father, ModelPerson mother, Connection conn) throws SQLException {
        int year_of_marriage = birth_year_of_one_individual + 18;
        String eventID = UUID.randomUUID().toString().replace("-", "");
        String eventID2 = eventID.substring(0,8);
        String eventID3 = UUID.randomUUID().toString().replace("-", "");
        String eventID4 = eventID3.substring(0,8);

        JSONObject obj = returnObjectFromFile(LOCATIONS);
        ModelEvent marriage1 = coder.changetoModelEvents(obj);
        marriage1.setEventID(eventID2);
        marriage1.setPersonID(father.getPersonID());
        marriage1.setAssociatedUsername(father.getAssociatedUsername());
        marriage1.setEventType("marriage");
        marriage1.setYear(year_of_marriage);

        ModelEvent marriage2 = coder.changetoModelEvents(obj);
        marriage2.setEventID(eventID4);
        marriage2.setPersonID(mother.getPersonID());
        marriage2.setAssociatedUsername(mother.getAssociatedUsername());
        marriage2.setEventType("marriage");
        marriage2.setYear(year_of_marriage);

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
}
