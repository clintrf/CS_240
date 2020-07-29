package serviceClasses;

import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import handlerClasses.EncoderDecoder;
import serviceClasses.requestService.RequestLoad;
import serviceClasses.requestService.RequestLogin;
import serviceClasses.requestService.RequestRegister;
import serviceClasses.resultService.*;

import java.util.UUID;

public class Services {

    private EncoderDecoder coder;
    private DatabaseDatabase database;

    private ResultsClear clearResult;
    private ResultsEvent eventResult;
    private ResultsEvents eventsResult;
    private ResultsFill fillResult;
    private ResultsLoad loadResult;
    private ResultsLogin loginResult;
    private ResultsPeople peopleResult;
    private ResultsPerson personResult;
    private ResultsRegister registerResult;

    private RequestRegister registerRequest;
    private RequestLogin loginRequest;
    private RequestLoad loadRequest;


    public Services() throws DatabaseException {
        coder = new EncoderDecoder();
        database = new DatabaseDatabase();

        clearResult = new ResultsClear(database);
        eventResult = new ResultsEvent(database);
        eventsResult = new ResultsEvents(database);
        fillResult = new ResultsFill(database);
        loadResult = new ResultsLoad(database);
        loginResult = new ResultsLogin(database);
        peopleResult = new ResultsPeople(database);
        personResult = new ResultsPerson(database);
        registerResult = new ResultsRegister(database);

        registerRequest = new RequestRegister();
        loginRequest = new RequestLogin();
        loadRequest = new RequestLoad();
    }

    public static String getRandomId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    public EncoderDecoder getCoder(){
        return coder;
    }
    public DatabaseDatabase getDatabase(){
        return database;
    }
    public ResultsClear getClearResult(){
        return clearResult;
    }
    public ResultsEvent getEventResult(){
        return eventResult;
    }

    public ResultsEvents getEventsResult() {
        return eventsResult;
    }

    public ResultsFill getFillResult() {
        return fillResult;
    }

    public ResultsLoad getLoadResult(){
        return loadResult;
    }

    public ResultsLogin getLoginResult() {
        return loginResult;
    }

    public ResultsPeople getPeopleResult() {
        return peopleResult;
    }

    public ResultsPerson getPersonResult() {
        return personResult;
    }

    public ResultsRegister getRegisterResult() {
        return registerResult;
    }

    public RequestRegister getRegisterRequest() {
        return registerRequest;
    }

    public RequestLogin getLoginRequest() {
        return loginRequest;
    }

    public RequestLoad getLoadRequest() {
        return loadRequest;
    }
}
