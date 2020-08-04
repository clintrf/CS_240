package serviceClasses;

import java.sql.SQLException;
import java.util.ArrayList;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoEvent;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import modelClasses.*;

public class ResponseEvents {

    private DatabaseDatabase database;
    private DaoAuthToken tokenDao;
    private DaoEvent eventDao;

    private ArrayList<ModelEvent> data;
    private Boolean success;
    private String message;


    public ResponseEvents(DatabaseDatabase database) {
        this.database = database;
        this.tokenDao = database.getTokenDao();
        this.eventDao = database.getEventsDao();

        setData(null);
        setSuccess(false);
        setMessage("Fail");
    }

    public ResponseEvents(String authToken){
        eventsResult(authToken);
    }

    public void eventsResult(String authToken)  {
        try {
            ModelAuthToken tokenObj = tokenDao.getAuthTokenByToken(authToken,database.getConnection());
            if (tokenObj.getUserName() == null){
                setData(null);
                setMessage("invalid auth token");
                setSuccess(false);
                return;
            }
            ArrayList<ModelEvent> eventObj = eventDao.findEventsByAssociatedUserName(tokenObj.getUserName(),database.getConnection());
            if(eventObj == null) {
                setData(null);
                setMessage("No events related to username");
                setSuccess(false);
                return;
            }
            if(eventObj.size() == 0){
                setData(null);
                setMessage("User has no events");
                setSuccess(false);
                return;
            }

            setData(eventObj);
            setMessage("Success");
            setSuccess(true);

        } catch (DatabaseException | SQLException e) {
            setData(null);
            setMessage("Error Database");
            setSuccess(false);
            e.printStackTrace();
        }
    }



    public void setData(ArrayList<ModelEvent> data) {
        this.data = data;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public ArrayList<ModelEvent> getData() {
        return this.data;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }
}
