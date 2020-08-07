package main.java.serviceClasses;

import main.java.daoClasses.*;
import java.lang.*;


public class Services {
    private DatabaseDatabase database = new DatabaseDatabase();

    public ResponseRegister register(RequestRegister request) {
        return ResponseRegister.register(request, database.getConnection());
    }
    public ResponseLogin login(RequestLogin request){
        return ResponseLogin.login(request,database.getConnection());
    }
    public ResponseClear clear(){
        return ResponseClear.clear(database.getConnection());
    }
    public ResponseFill fill(String username, int generations) {
        return ResponseFill.fill(username,generations,database.getConnection());
    }
    public ResponseLoad load(RequestLoad request){
        return ResponseLoad.load(request,database.getConnection());
    }
    public ResponsePerson person(String auth_token, String personID){
        return ResponsePerson.person(auth_token,personID,database.getConnection());
    }
    public ResponsePeople people(String auth_token){
        return ResponsePeople.people(auth_token,database.getConnection());
    }
    public ResponseEvent event(String auth_token, String eventID){
        return ResponseEvent.event(auth_token,eventID,database.getConnection());
    }
    public ResponseEvents events(String auth_token){
        return ResponseEvents.events(auth_token,database.getConnection());
    }

}