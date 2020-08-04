package serviceClasses;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoEvent;
import dataAccessClasses.DaoPerson;
import dataAccessClasses.DaoUser;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;

import java.security.PublicKey;

/**
 * Response Body for Clear
 */
public class ResponseClear {
    private DatabaseDatabase database;
    private DaoAuthToken tokenDao;
    private DaoEvent eventDao;
    private DaoUser userDao;
    private DaoPerson personDao;

    private Boolean success;
    private String message;

    public ResponseClear(DatabaseDatabase database) {
        this.database = database;
        this.tokenDao = database.getTokenDao();
        this.eventDao = database.getEventsDao();
        this.userDao = database.getUserDao();
        this.personDao = database.getPersonDao();

        this.success = false;
        this.message = "Error";
    }

    public ResponseClear(){
        clearResult();
    }

    public void clearResult(){

        try {
            tokenDao.drop(database.getConnection());
            eventDao.drop(database.getConnection());
            personDao.drop(database.getConnection());
            userDao.drop(database.getConnection());

            tokenDao.create(database.getConnection());
            eventDao.create(database.getConnection());
            personDao.create(database.getConnection());
            userDao.create(database.getConnection());

            setMessage("clear succeeded");
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
