package serviceClasses.resultService;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoEvent;
import dataAccessClasses.DaoPerson;
import dataAccessClasses.DaoUser;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;

import java.sql.Connection;

/**
 * Response Body for Clear
 */
public class ResultsClear {

    /**
     * Boolean to indicate if Response Body is a success
     */
    private Boolean success;

    /**
     * Message to describe if Response Body succeeded or failed
     */
    private String message;

    /**
     * Constructor for resultsClear
     */
    public ResultsClear() {
        this.success = false;
        this.message = "Error";
        DatabaseDatabase database = new DatabaseDatabase();

        try {
            Connection conn = database.openConnection();
            DaoAuthToken tokenObj = new DaoAuthToken(conn);
            DaoEvent eventObj = new DaoEvent(conn);
            DaoPerson personObj = new DaoPerson(conn);
            DaoUser userObj = new DaoUser(conn);

            tokenObj.drop();
            eventObj.drop();
            personObj.drop();
            userObj.drop();

            tokenObj.create();
            eventObj.create();
            personObj.create();
            userObj.create();

            setMessage("Clear and create success");
            setSuccess(true);

        } catch (DatabaseException e) {
            setMessage("Clear and create fail");
            setSuccess(false);
            e.printStackTrace();
        }


    }

    /**
     * Sets the success variable
     * @param success Indicates if the Response Body was a success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * Sets the message variable to describe the success or fail
     * @param message Description of success or fail
     */
    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * Gets the success variable
     * @return success
     */
    public Boolean getSuccess() {
        return this.success;
    }

    /**
     * Gets the message variable
     * @return message
     */
    public String getMessage() {
        return this.message;
    }
}
