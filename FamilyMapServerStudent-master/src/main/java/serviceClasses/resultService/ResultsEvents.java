package serviceClasses.resultService;

import java.sql.Connection;
import java.util.ArrayList;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoEvent;
import dataAccessClasses.DaoPerson;
import dataAccessClasses.DaoUser;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import handlerClasses.EncoderDecoder;
import handlerClasses.Handler;
import modelClasses.*;

public class ResultsEvents {

    private DaoAuthToken tokenDao;
    private DaoEvent eventDao;

    private ArrayList<ModelEvent> data;
    private Boolean success;
    private String message;


    public ResultsEvents(DatabaseDatabase database) {
        tokenDao = database.getTokenDao();
        eventDao = database.getEventsDao();

        setData(null);
        setSuccess(false);
        setMessage("Fail");
    }

    public ResultsEvents(String authToken){
        eventsResult(authToken);
    }

    public void eventsResult(String authToken)  {
        try {
            ModelAuthToken tokenObj = tokenDao.findAuthTokenByToken(authToken);
            if (tokenObj.getUserName() == null){
                setData(null);
                setMessage("invalid auth token");
                setSuccess(false);
                return;
            }
            ArrayList<ModelEvent> eventObj = eventDao.findEventsByAssociatedUserName(tokenObj.getUserName());
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

        } catch (DatabaseException e) {
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
