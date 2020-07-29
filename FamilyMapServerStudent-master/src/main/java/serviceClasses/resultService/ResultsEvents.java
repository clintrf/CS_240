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

    public void eventsResult(String authToken)  {
        try { ModelAuthToken tokenObj = tokenDao.findAuthTokenByToken(authToken);
            try { ArrayList<ModelEvent> eventObj = eventDao.findEventsByAssociatedUserName(tokenObj.getUserName());
                if(eventObj.size() == 0){
                    setMessage("No events related to username");
                    setSuccess(false);
                    return;
                }
                setData(eventObj);
                setMessage("Success");
                setSuccess(true);
            } catch (DatabaseException e) {
                setMessage("No user found with username");
                setSuccess(false);
                e.printStackTrace();
            }
        } catch (DatabaseException e) {
            setMessage("invalid auth token");
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
