package serviceClasses.resultService;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoEvent;
import dataAccessClasses.DaoPerson;
import dataAccessClasses.DaoUser;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;

/**
 * Response Body for Clear
 */
public class ResultsClear {
    private DaoAuthToken tokenDao;
    private DaoEvent eventDao;
    private DaoUser userDao;
    private DaoPerson personDao;

    private Boolean success;
    private String message;

    public ResultsClear(DatabaseDatabase database) {
        tokenDao = database.getTokenDao();
        eventDao = database.getEventsDao();
        userDao = database.getUserDao();
        personDao = database.getPersonDao();

        this.success = false;
        this.message = "Error";
    }

    public void clearResult(){

        try {
            tokenDao.drop();
            eventDao.drop();
            personDao.drop();
            userDao.drop();

            tokenDao.create();
            eventDao.create();
            personDao.create();
            userDao.create();

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
