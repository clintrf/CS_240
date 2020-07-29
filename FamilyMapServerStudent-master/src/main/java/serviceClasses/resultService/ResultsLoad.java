package serviceClasses.resultService;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoEvent;
import dataAccessClasses.DaoPerson;
import dataAccessClasses.DaoUser;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import modelClasses.ModelEvent;
import modelClasses.ModelPerson;
import modelClasses.ModelUser;
import serviceClasses.requestService.RequestLoad;

import java.sql.Connection;

/**
 * Response Body for Load
 */
public class ResultsLoad {

    private DaoUser userDao;
    private DaoPerson personDao;
    private DaoEvent eventDao;

    private Boolean success;
    private String message;

    public ResultsLoad(DatabaseDatabase database) {
        this.userDao = database.getUserDao();
        this.personDao = database.getPersonDao();
        this.eventDao = database.getEventsDao();

        setSuccess(false);
        setMessage("Fail");
    }



    public void loadResult(RequestLoad requestLoad) throws DatabaseException {
        try {
            for (ModelUser u : requestLoad.getUsers()) {
                userDao.insert(u);
            }
        } catch (DatabaseException e) {
            setMessage("Didnt add user in load to database");
            setSuccess(false);
            e.printStackTrace();
        }

        try {
            for (ModelPerson p : requestLoad.getPersons()) {
                personDao.insert(p);
            }
        } catch (DatabaseException e) {
            setMessage("Didnt add person in load to database");
            setSuccess(false);
            e.printStackTrace();
        }

        try {
            for (ModelEvent e : requestLoad.getEvents()) {
                eventDao.insert(e);
            }
        } catch (DatabaseException e) {
            setMessage("Didnt add event in load to database");
            setSuccess(false);
            e.printStackTrace();
        }

        setMessage("Success in load person/ users");
        setSuccess(true);

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