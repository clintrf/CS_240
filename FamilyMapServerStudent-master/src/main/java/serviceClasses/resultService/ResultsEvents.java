package serviceClasses.resultService;

import java.sql.Connection;
import java.util.ArrayList;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoEvent;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import modelClasses.*;

public class ResultsEvents {

    private ArrayList<ModelEvent> data;
    private Boolean success;
    private String message;

    public ResultsEvents(String authToken) throws DatabaseException {
        this.data = new ArrayList<ModelEvent>();
        this.success = false;
        this.message = "Error";

        DatabaseDatabase database = new DatabaseDatabase();
        Connection conn = database.openConnection();
        DaoAuthToken tokenDao = new DaoAuthToken(conn);
        DaoEvent eventDao = new DaoEvent(conn);

        String username;

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
