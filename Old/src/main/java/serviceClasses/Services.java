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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import serviceClasses.requestService.RequestLoad;
import serviceClasses.requestService.RequestLogin;
import serviceClasses.requestService.RequestRegister;
import serviceClasses.resultService.*;

import java.io.FileReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Services {

    private EncoderDecoder coder = new EncoderDecoder();
//    private DatabaseDatabase database;
//
////    private ResultsClear clearResult;
////    private ResultsEvent eventResult;
////    private ResultsEvents eventsResult;
////    private ResultsFill fillResult;
////    private ResultsLoad loadResult;
////    private ResultsLogin loginResult;
////    private ResultsPeople peopleResult;
////    private ResultsPerson personResult;
////    private ResultsRegister registerResult;
////
////    private RequestRegister registerRequest;
////    private RequestLogin loginRequest;
////    private RequestLoad loadRequest;
//
//
//    public Services() {
//        coder = new EncoderDecoder();
//        database = new DatabaseDatabase();
//
////        clearResult = new ResultsClear(database);
////        eventResult = new ResultsEvent(database);
////        eventsResult = new ResultsEvents(database);
////        fillResult = new ResultsFill(database);
////        loadResult = new ResultsLoad(database);
////        loginResult = new ResultsLogin(database);
////        peopleResult = new ResultsPeople(database);
////        personResult = new ResultsPerson(database);
////        registerResult = new ResultsRegister(database);
////
////        registerRequest = new RequestRegister();
////        loginRequest = new RequestLogin();
////        loadRequest = new RequestLoad();
//    }
//
    public static String getRandomId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    public EncoderDecoder getCoder(){
        return coder;
    }
//    public DatabaseDatabase getDatabase(){
//        return database;
//    }
//    public ResultsClear getClearResult(){
//        return clearResult;
//    }
//    public ResultsEvent getEventResult(){
//        return eventResult;
//    }
//
//    public ResultsEvents getEventsResult() {
//        return eventsResult;
//    }
//
//    public ResultsFill getFillResult() {
//        return fillResult;
//    }
//
//    public ResultsLoad getLoadResult(){
//        return loadResult;
//    }
//
//    public ResultsLogin getLoginResult() {
//        return loginResult;
//    }
//
//    public ResultsPeople getPeopleResult() {
//        return peopleResult;
//    }
//
//    public ResultsPerson getPersonResult() {
//        return personResult;
//    }
//
//    public ResultsRegister getRegisterResult() {
//        return registerResult;
//    }
//
//    public RequestRegister getRegisterRequest() {
//        return registerRequest;
//    }
//
//    public RequestLogin getLoginRequest() {
//        return loginRequest;
//    }
//
//    public RequestLoad getLoadRequest() {
//        return loadRequest;
//    }
    public ResultsClear getClearResult(){
        return new ResultsClear(database);
    }
    public ResultsEvent getEventResult(){
        return new ResultsEvent(database);
    }

    public ResultsEvents getEventsResult() {
        return new ResultsEvents(database);
    }

    public ResultsFill getFillResult() {
        return new ResultsFill(database);
    }

    public ResultsLoad getLoadResult(){
        return new ResultsLoad(database);
    }

    public ResultsLogin getLoginResult() {
        return new ResultsLogin(database);
    }

    public ResultsPeople getPeopleResult() {
        return new ResultsPeople(database);
    }

    public ResultsPerson getPersonResult() {
        return new ResultsPerson(database);
    }

    public ResultsRegister getRegisterResult() {
        return new ResultsRegister(database);
    }

    public RequestRegister getRegisterRequest() {
        return new RequestRegister();
    }

    public RequestLogin getLoginRequest() {
        return new RequestLogin();
    }

    public RequestLoad getLoadRequest() {
        return new RequestLoad();
    }


    //private EncoderDecoder coder = new EncoderDecoder();
    private DatabaseDatabase database = new DatabaseDatabase();

    Connection conn = database.openConnection();




    public ResultsRegister registerResult(RequestRegister request) {
        ResultsRegister result = new ResultsRegister(database);
        String authTokenTemp = (Services.getRandomId());
        String personID =  (Services.getRandomId());

        ModelUser user = coder.decodeToModelUser(coder.encodeRegister(request));
        user.setPersonID(personID);

        ModelAuthToken authToken = new ModelAuthToken(
                authTokenTemp,
                user.getUserName(),
                user.getPassword()
        );

        DaoUser userDao = new DaoUser(conn);
        DaoAuthToken tokenDao = new DaoAuthToken(conn);

        try{
            userDao.insert(user);
            tokenDao.insert(authToken);
            result.setMessage("added to database in ResultsRegister service");
            result.setSuccess(true);
        } catch (DatabaseException e) {
            result.setUserName(null);
            result.setAuthToken(null);
            result.setPersonId(null);
            result.setMessage("Could not add to database in ResultsRegister service");
            result.setSuccess(false);
            e.printStackTrace();
        }
        fillResult(user.getUserName(),3);

        result.setUserName(user.getUserName());
        result.setPersonId(personID);
        result.setAuthToken(authTokenTemp);
        return result;
    }
    /*

    public ResponseLogin login(RequestLogin request){
        ResponseLogin response = new ResponseLogin();
        ModelUsers userObject;
//        Connection conn = database.openConnection();
        try{
            AccessUsers userAccess = new AccessUsers();
            userObject = userAccess.getRow(request.getuserName(), conn); //may throw exception
            if(!(userObject.getpassword().equals(request.getpassword()))){ //checks to make sure password matches
                throw new Exception();
            }
        }catch(SQLException ex){
            String message = "User is not in database";
            response.setmessage(message);
//            database.closeConnection();
            return response;
        }catch(Exception ex){
            String message = "Credentials not applicable";
            response.setmessage(message);
//            database.closeConnection();
            return response;
        }

        AccessAuthTokens accessTokens = new AccessAuthTokens();
        String returnusername = userObject.getuserName();
        String returnpersonID = userObject.getpersonID();
        ModelAuthTokens tokensObject = accessTokens.getRowByUsername(returnusername, conn);

//        database.closeConnection();
        //creates new auth_token
        String auth = UUID.randomUUID().toString().replace("-", "");
        String auth2 = auth.substring(0,8); //personID generated

        //needs to store in database here
        try {
            accessTokens.createRow(new ModelAuthTokens(returnusername, auth2), conn);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        response.setpersonID(returnpersonID);
        response.setuserName(returnusername);
        response.setauthToken(auth2);

        return response;
        //grabs information from table to send back to user if successful

    }


    public ResponseClear clear(){ //how can this have an errors?
        ResponseClear response = new ResponseClear();
//        Connection conn = database.openConnection();

        AccessAuthTokens tokenAccess = new AccessAuthTokens();
        AccessUsers userAccess = new AccessUsers();
        AccessEvents eventAccess = new AccessEvents();
        AccessPersons personAccess = new AccessPersons();

        tokenAccess.dropTable(conn);
        userAccess.dropTable(conn);
        eventAccess.dropTable(conn);
        personAccess.dropTable(conn);

        tokenAccess.createTable(conn);
        userAccess.createTable(conn);
        eventAccess.createTable(conn);
        personAccess.createTable(conn);

//        database.closeConnection();

        String message = "Clear succeeded.";
        response.setmessage(message);

        return response;
    }

    public void deleteAllPertainingtoUser(String username){

        //deletes all events and people pertaining to the user
        AccessEvents eventsAccess = new AccessEvents();
        AccessPersons personAccess = new AccessPersons();
//        ModelPersons person = null;
//        try {
//            person = personAccess.getRowByUsername(username, conn);
//        }catch(SQLException ex){
//            ex.printStackTrace();
//        }
        //search for people by person ID or by descendant
//        String personID = person.getpersonID();
//        String descendant = person.getdescendant();

        eventsAccess.removeRowDescendant(username, conn);
//        eventsAccess.removeRowPersonID(personID, conn);
        personAccess.removeRowByDescendant(username, conn); //person is own descendant

        //all rows relating to the person should be removed from database

    }

*/
    public ResultsFill fillResult( String username, Integer genNum ) {

        ResultsFill result = new ResultsFill(database);
        DaoUser userDao = new DaoUser(conn);
        try{
            ModelUser userObj = userDao.findUserByUserName(username);
            if(userObj.getUserName() == null){
                result.setSuccess(false);
                result.setMessage("Cant find user in database");
                return result;
            }
            DaoEvent eventDao = new DaoEvent(conn);
            DaoPerson personDao = new DaoPerson(conn);

            eventDao.removeEventById(userObj.getPersonID());
            personDao.removePersonById(userObj.getPersonID());

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
                        personDao.insert(obj);
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
                    personDao.insert(obj); //add the last generation
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


        } catch (DatabaseException | NullPointerException e) {
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
        DaoEvent eventDao = new DaoEvent(conn);
        try {
            eventDao.insert(birth);
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
        DaoEvent eventDao = new DaoEvent(conn);
        try {
            eventDao.insert(death);
        }catch(DatabaseException ex){
            ex.printStackTrace();
        }
    }

    public void createMarriage(ModelPerson father, ModelPerson mother) throws DatabaseException {
        Integer birthYear = 0;
        DaoEvent eventDao = new DaoEvent(conn);
        for(ModelEvent e:eventDao.findEventsByAssociatedUserName(mother.getAssociatedUsername()) ){
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
            ResultsFill.NamesData data = gson.fromJson(new FileReader(filename), ResultsFill.NamesData.class);
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

/*
    public ResponseLoad load(RequestLoad request){
        ResponseLoad response = new ResponseLoad();

        //clear all information here
        clear();

        AccessEvents eventAccess = new AccessEvents();
        AccessUsers userAccess = new AccessUsers();
        AccessPersons personAccess = new AccessPersons();

        int num_persons = 0;
        int num_users = 0;
        int num_events = 0;

        for(ModelPersons person: request.getPersons()){
            try {
                personAccess.createRow(person, conn);
                num_persons++;
            }catch(SQLException ex){
                String message = "In Load: could not add person to database.";
                response.setmessage(message);
                return response;
            }
        }
        for(ModelUsers user: request.getUsers()){ //should we create an auth token for these people?
            try{
                userAccess.createRow(user, conn);
                num_users++;
            }catch(SQLException ex){
                String message = "In Load: could not add person to database.";
                response.setmessage(message);
                return response;
            }
        }
        for(ModelEvents event: request.getEvents()){
            try{
                eventAccess.createRow(event, conn);
                num_events++;
            }catch(SQLException ex){
                String message = "In Load: could not add person to database.";
                response.setmessage(message);
                return response;
            }
        }

        String message = "Successfully added " + num_users + " users, " + num_persons + " persons, and " + num_events + " events to the database.";
        response.setmessage(message);
        return response;
    }


    public ResponsePerson person(String auth_token, String personID){

        ResponsePerson response = new ResponsePerson();
//        Connection conn = database.openConnection();
        AccessPersons personAccess = new AccessPersons();
        AccessAuthTokens tokenAccess = new AccessAuthTokens();
//        Connection conn = database.openConnection();
        String username;
        try {
            ModelAuthTokens tokenObject = tokenAccess.getRow(auth_token, conn);
            String descendant = tokenObject.getuserName();
            ModelPersons person = personAccess.getRow(personID, conn);
            if(!(person.getdescendant().equals(descendant))){
                String message = "You are not authorized to access this.";
                response.setmessage(message);
                return response;
            }
//            username = tokenObject.getuserName()()();
        }catch(SQLException ex){
            String message = "Invalid auth_token.";
            response.setmessage(message);
//            database.closeConnection();
            return response;
        }

        try {
            ModelPersons personObject = personAccess.getRow(personID, conn);
            response = coder.decodetoResponsePerson(coder.encodeModelPersons(personObject));
//            System.out.println(coder.encodeModelPersons(personObject));
        }catch(SQLException ex){
            String message = "Person is not in database.";
            response.setmessage(message);
//            database.closeConnection();
            return response;
        }

        return response;
    }


    public ResponsePeople people(String auth_token){
        ResponsePeople response = new ResponsePeople();
        AccessAuthTokens tokenAccess = new AccessAuthTokens();
//        Connection conn = database.openConnection();
        String username;
        try {
            ModelAuthTokens tokenObject = tokenAccess.getRow(auth_token, conn);
            username = tokenObject.getuserName();
        }catch(SQLException ex){
            String message = "Invalid auth_token.";
            response.setmessage(message);
//            database.closeConnection();
            return response;
        }

        AccessPersons personAccess = new AccessPersons();
        ArrayList<ModelPersons> data;
        try {
            data = personAccess.getRows(username, conn);
        }catch(SQLException ex){
            String message = "No persons related to this user.";
            response.setmessage(message);
            return response;
        }

        if(data.size() == 0) {
            String message = "There are no persons related to this user in the database";
            response.setmessage(message);
            response.setnull();
            return response;
        }

        response.setdata(data);

        return response;
    }


    public ResponseEvent event(String auth_token, String eventID){
        ResponseEvent response = new ResponseEvent();
//        Connection conn = database.openConnection();
        AccessAuthTokens tokenAccess = new AccessAuthTokens();
        AccessEvents eventAccess = new AccessEvents();
//        Connection conn = database.openConnection();
        ModelAuthTokens tokenObject;
        try {
            tokenObject = tokenAccess.getRow(auth_token, conn);
            String descendant = tokenObject.getuserName();
            ModelEvents event = eventAccess.getRow(eventID, conn); //gets the event to compare descendants
            if(!(event.getdescendant().equals(descendant))){
                String message = "You are not authorized to access this.";
                response.setmessage(message);
                return response;
            }
        }catch(SQLException ex){
            String message = "Invalid auth_token.";
            response.setmessage(message);
//            database.closeConnection();
            return response;
        }
        ModelEvents eventObject = new ModelEvents(null, null, null, null, null, null, null, null, null);
        try {
            eventObject = eventAccess.getRow(eventID, conn);
            response = coder.decodetoResponseEvent(coder.encodeModelEvents(eventObject));
        }catch(SQLException ex){
            //need to set the evenObject to null;
            eventObject.setNull();
            String message = "Event is not in database.";
            response.setmessage(message);
//            database.closeConnection();
            return response;
        }
//        database.closeConnection();
        return response;
    }

    public ResponseEvents events(String auth_token){
        ResponseEvents response = new ResponseEvents();
        AccessAuthTokens tokenAccess = new AccessAuthTokens();
//        Connection conn = database.openConnection();
        String username;
        try {
            ModelAuthTokens tokenObject = tokenAccess.getRow(auth_token, conn);
            username = tokenObject.getuserName();
        }catch(SQLException ex){
            String message = "Invalid auth_token.";
            response.setmessage(message);
            return response;
        }

        System.out.println("USERNAME: " + username);
//
        AccessEvents eventAccess = new AccessEvents();
        ArrayList<ModelEvents> data;
        try {
            data = eventAccess.getRows(username, conn);
        }catch(SQLException ex){
            String message = "No persons related to this user.";
            response.setmessage(message);
            return response;
        } //has a list of all people related to user at this point

        if(data.size() == 0) {
            String message = "There are no events related to this user in the database";
            response.setmessage(message);
            response.setnull();
            return response;
        }
        response.setevents(data);

        return response;

    }

     */
}
