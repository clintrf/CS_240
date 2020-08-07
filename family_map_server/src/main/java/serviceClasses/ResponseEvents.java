package main.java.serviceClasses;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import main.java.daoClasses.DaoAuthToken;
import main.java.daoClasses.DaoEvent;
import main.java.modelClasses.ModelAuthTokens;
import main.java.modelClasses.ModelEvents;

public class ResponseEvents{

    private ArrayList<ModelEvents> data;
    private String message;
    private Boolean success;

    public void setData(ArrayList<ModelEvents> data){ this.data = data; }
    public void setMessage(String message){ this.message = message; }
    public void setSuccess(Boolean success) { this.success = success; }

    public ArrayList<ModelEvents> getData() { return this.data; }
    public String getMessage() { return this.message; }
    public Boolean getSuccess() { return this.success; }

    public static ResponseEvents events(String authToken, Connection conn){
        ResponseEvents eventsResponse = new ResponseEvents();

        DaoAuthToken authTokenDao = new DaoAuthToken(conn);
        DaoEvent eventDao = new DaoEvent(conn);

        ArrayList<ModelEvents> data;
        ModelAuthTokens authTokenModel;
        try {
            authTokenModel = authTokenDao.getAuthTokenByToken(authToken);
            if(authTokenModel == null){
                throw new SQLException();
            }
            data = eventDao.getEventsByAssociatedUsername(authTokenModel.getUserName());
            if(data == null){
                throw new SQLException();
            }
            if(data.size() == 0) {
                throw new SQLException();
            }

            eventsResponse.setData(data);
            eventsResponse.setSuccess(true);
            return eventsResponse;
        }catch(SQLException e){
            eventsResponse.setData(null);
            eventsResponse.setMessage("error");
            eventsResponse.setSuccess(false);
            return eventsResponse;
        }


    }
}