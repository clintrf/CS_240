
package main.java.serviceClasses;

import main.java.dataAccessClasses.*;
import main.java.modelClasses.ModelAuthTokens;
import main.java.modelClasses.ModelEvents;
import main.java.modelClasses.ModelPersons;
import main.java.modelClasses.ModelUsers;

import java.util.*;
import java.sql.*;


public class ServerFacade {
    private EncodeDecode coder = new EncodeDecode();
    private Database database = new Database();
    private Creator create = new Creator();
    Connection conn = database.openConnection();


    public ResponseRegister register(RequestRegister request) throws SQLException { //fill 4 here for the user?
        ResponseRegister response = new ResponseRegister();

        System.out.println("IN SERVER FACADE");

        String convert = coder.encodeRegister(request);
        ModelUsers userObject = coder.decodetoModelUser(convert);

        String personID = UUID.randomUUID().toString().replace("-", "");
        String auth_token = UUID.randomUUID().toString().replace("-", "");

        String personID2 = personID.substring(0,8); //personID generated
        String auth_token2 = auth_token.substring(0,8); //auth_token generated

        userObject.setPersonID(personID2);
        ModelAuthTokens authTokenObject = new ModelAuthTokens( auth_token2, userObject.getUserName(),userObject.getPassword() );

        DaoUser userAccess = new DaoUser();
        DaoAuthToken tokenAccess = new DaoAuthToken();
        try{
            userAccess.insert(userObject, conn); //one of these is throwing an exception
            tokenAccess.insert(authTokenObject, conn);
            response.setMessage("added to database in ResultsRegister service");
            response.setSuccess(true);
        }catch(SQLException ex){
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
        ModelUsers userObject;
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
            ModelAuthTokens tokensObject = accessTokens.getAuthTokenByUsername(returnusername, conn);
        } catch (Exception ex) {
            String message = "Error: cant tink authToken by userName";
            response.setMessage(message);
            response.setSuccess(false);
            return response;
        }

            String auth = UUID.randomUUID().toString().replace("-", "");
        String auth2 = auth.substring(0, 8); //personID generated

        try {
            accessTokens.insert(new ModelAuthTokens(auth2, returnusername, userObject.getPassword() ), conn);
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

    public ResponseClear clear(){ //how can this have an errors?
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

    public void deleteAllPertainingtoUser(String username){

        DaoEvent eventsAccess = new DaoEvent();
        DaoPerson personAccess = new DaoPerson();

        eventsAccess.removeEventByAssociatedUsername(username, conn);
        personAccess.removePersonByAssociatedUsername(username, conn); //person is own descendant

    }
    public ResponseFill fill(String username, int generations) throws SQLException {

        ResponseFill response = new ResponseFill();
        DaoUser userAccess = new DaoUser();
        ModelUsers user;
        try {
            user = userAccess.getUserByUsername(username, conn);
        }catch(SQLException ex){
            response.setMessage("Unable to locate user in database.");
            return response;
        }

        deleteAllPertainingtoUser(username);

        create.seteventstozero(); //reset events added to zero

        String convert = coder.encodeModelUsers(user);
        ModelPersons userPerson = coder.decodetoModelPersons(convert);
        userPerson.setAssociatedUsername(username);
        create.createBirth(userPerson, username, conn); //adds own users birth

        ArrayList<ModelPersons> useList = new ArrayList<ModelPersons>();
        ArrayList<ModelPersons> nextList = new ArrayList<ModelPersons>();
        DaoPerson personAccess = new DaoPerson();
        nextList.add(userPerson);

        int num_people = 0;
        for(int i = 1; i <= generations; i++){
            useList.clear();
            for(ModelPersons p: nextList){
                useList.add(p);
            }
            nextList.clear();
            for(ModelPersons obj: useList){
                ArrayList<ModelPersons> parents = create.createCouple(username, conn, i, obj);
                obj.setFatherID(parents.get(0).getPersonID()); //assign IDs to the proper child
                obj.setMotherID(parents.get(1).getPersonID());
                try {
                    personAccess.insert(obj, conn); //add the person to the database
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
                personAccess.insert(obj, conn); //add the last generation
            }catch(SQLException ex) {
                String message = "Could not add person in fill to database.";
                response.setMessage(message);
                return response;
            }
            num_people++;
        }

        response.setMessage("Successfully added " + num_people + " persons and " + create.getevents()+  " events to the database.");
        return response;
    }

    public ResponseFill fill(String username) throws SQLException {

        final int default_generations = 4;
        ResponseFill response = new ResponseFill();
        DaoUser userAccess = new DaoUser();
        ModelUsers user;
        try {
            user = userAccess.getUserByUsername(username, conn);
        }catch(SQLException ex){
            response.setMessage("Unable to locate user in database.");
            return response;
        }

        deleteAllPertainingtoUser(username);

        create.seteventstozero(); //reset events added to zero

        String convert = coder.encodeModelUsers(user);
        ModelPersons userPerson = coder.decodetoModelPersons(convert);
        userPerson.setAssociatedUsername(username);
        create.createBirth(userPerson, username, conn); //adds own users birth

        ArrayList<ModelPersons> useList = new ArrayList<ModelPersons>();
        ArrayList<ModelPersons> nextList = new ArrayList<ModelPersons>();
        DaoPerson personAccess = new DaoPerson();
        nextList.add(userPerson);



        int num_people = 0;
        for(int i = 1; i <= default_generations; i++){
            useList.clear();
            for(ModelPersons p: nextList){
                useList.add(p);
            }
            nextList.clear();
            for(ModelPersons obj: useList){
                ArrayList<ModelPersons> parents = create.createCouple(username, conn, i, obj);
                obj.setFatherID(parents.get(0).getPersonID()); //assign IDs to the proper child
                obj.setMotherID(parents.get(1).getPersonID());
                try {
                    personAccess.insert(obj, conn); //add the person to the database
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
                personAccess.insert(obj, conn); //add the last generation
            }catch(SQLException ex) {
                String message = "Could not add person in fill to database.";
                response.setMessage(message);
                return response;
            }
            num_people++;
        }

        response.setMessage("Successfully added " + num_people + " persons and " + create.getevents()+  " events to the database.");
        return response;
    }

    /**Takes a request Object containing request information and returns an object containing response information
     *@param request a RequestLoad object containing information to complete a load request
     */
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
//                if(person.getAssociatedUsername() == null){
//
//                    person.setAssociatedUsername(userAccess.getRowbyPersonID(person.getPersonID(),conn).getUserName());
//                }
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
//                if(event.getAssociatedUsername() == null){
//                    event.setAssociatedUsername(personAccess.getPersonByID(event.getPersonID(),conn).getAssociatedUsername());
//                }
                eventAccess.insert(event, conn);
                num_events++;
            }catch(SQLException ex){
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

    /**Retrieves a person from the server and returns an object containing response information
     *@param personID passes in the eventID for the event information to return
     */
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
                String message = "error: You are not authorized to access this.";

                response.setMessage(message);
                response.setSuccess(false);
                return response;
            }
        }catch(SQLException ex){
            String message = "error: Invalid auth_token.";
            response.setMessage(message);
            return response;
        }

        try {
            ModelPersons personObject = personAccess.getPersonByID(personID, conn);
            response = coder.decodetoResponsePerson(coder.encodeModelPersons(personObject));

            response.setMessage("Successful");
            response.setSuccess(true);
            return response;
        }catch(SQLException ex){
            String message = "Error:Person is not in database.";
            response.setMessage(message);
            return response;
        }

    }

    /**Retrieves multiple persons from the server and returns an object containing response information
     */
    public ResponsePeople people(String auth_token){
        ResponsePeople response = new ResponsePeople();
        DaoAuthToken tokenAccess = new DaoAuthToken();
        String username;
        try {
            ModelAuthTokens tokenObject = tokenAccess.getAuthTokenByToken(auth_token, conn);
            username = tokenObject.getUserName();
        }catch(SQLException ex){
            String message = "error Invalid auth_token.";
            response.setMessage(message);
            return response;
        }

        DaoPerson personAccess = new DaoPerson();
        ArrayList<ModelPersons> data;
        try {
            data = personAccess.getPeopleByAssociatedUsername(username, conn);
        }catch(SQLException ex){
            String message = "error No persons related to this user.";
            response.setSuccess(false);
            response.setMessage(message);
            return response;
        }

        if(data.size() == 0) {
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

    /**Retrieves an event from the server and returns an object containing response information
     *@param eventID passes in the eventID for the event information to return
     */
    public ResponseEvent event(String auth_token, String eventID){
        ResponseEvent response = new ResponseEvent();
        DaoAuthToken tokenAccess = new DaoAuthToken();
        DaoEvent eventAccess = new DaoEvent();
        ModelAuthTokens tokenObject;
        try {
            tokenObject = tokenAccess.getAuthTokenByToken(auth_token, conn);
            String descendant = tokenObject.getUserName();
            ModelEvents event = eventAccess.getEventById(eventID, conn); //gets the event to compare descendants
            if(!(event.getAssociatedUsername().equals(descendant))){
                String message = "error You are not authorized to access this.";
                response.setMessage(message);
                response.setSuccess(false);
                return response;
            }
        }catch(SQLException ex){
            String message = "error Invalid auth_token.";
            response.setMessage(message);
            response.setSuccess(false);
            return response;
        }
        ModelEvents eventObject = new ModelEvents(null, null, null, null, null, null, null, null, null);
        try {
            eventObject = eventAccess.getEventById(eventID, conn);
            response = coder.decodetoResponseEvent(coder.encodeModelEvents(eventObject));
        }catch(SQLException ex){
            eventObject.setNull();
            String message = "error Event is not in database.";
            response.setMessage(message);
            response.setSuccess(false);
            return response;
        }
        response.setSuccess(true);
        return response;
    }

    /**Retrieves multiple events from the server and returns an object containing response information
     */
    public ResponseEvents events(String auth_token){
        ResponseEvents response = new ResponseEvents();
        DaoAuthToken tokenAccess = new DaoAuthToken();
        String username;
        try {
            ModelAuthTokens tokenObject = tokenAccess.getAuthTokenByToken(auth_token, conn);
            username = tokenObject.getUserName();
        }catch(SQLException ex){
            String message = "error Invalid auth_token.";
            response.setMessage(message);
            response.setSuccess(false);
            return response;
        }

        System.out.println("USERNAME: " + username);
//
        DaoEvent eventAccess = new DaoEvent();
        ArrayList<ModelEvents> data;
        try {
            data = eventAccess.getEventsByAssociatedUsername(username, conn);
        }catch(SQLException ex){
            String message = "error No persons related to this user.";
            response.setMessage(message);
            response.setSuccess(false);
            return response;
        } //has a list of all people related to user at this point

        if(data.size() == 0) {
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