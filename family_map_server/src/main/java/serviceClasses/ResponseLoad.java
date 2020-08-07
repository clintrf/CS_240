package main.java.serviceClasses;

import main.java.daoClasses.*;
import main.java.modelClasses.ModelEvents;
import main.java.modelClasses.ModelPersons;
import main.java.modelClasses.ModelUsers;
import java.sql.Connection;
import java.sql.SQLException;

public class ResponseLoad{

    private String message;
    private Boolean success;

    public void setMessage(String message){ this.message = message; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getMessage() { return this.message; }
    public Boolean getSuccess() { return this.success; }


    public static ResponseLoad load(RequestLoad loadRequest, Connection conn){
        ResponseClear.clear(conn);

        ResponseLoad loadResponse = new ResponseLoad();

        DaoEvent eventDao = new DaoEvent(conn);
        DaoUser userDao = new DaoUser(conn);
        DaoPerson personDao = new DaoPerson(conn);

        int num_persons = 0;
        int num_users = 0;
        int num_events = 0;

        try{
            for(ModelUsers user: loadRequest.getUsers()){
                userDao.insert(user);
                num_users++;
            }

            for(ModelPersons person: loadRequest.getPeople()){
                personDao.insert(person);
                num_persons++;
            }

            for(ModelEvents event: loadRequest.getEvents()){
                eventDao.insert(event);
                num_events++;
            }

            loadResponse.setMessage("Successfully added " + num_users + " users, " + num_persons + " persons, and " + num_events + " events to the database.");
            loadResponse.setSuccess(true);
            return loadResponse;
        } catch (SQLException e){
            loadResponse.setMessage("error");
            loadResponse.setSuccess(false);
            return loadResponse;
        }



    }

}