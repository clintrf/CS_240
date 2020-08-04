package serviceClasses;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoEvent;
import dataAccessClasses.DaoPerson;
import dataAccessClasses.DaoUser;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import modelClasses.ModelEvent;
import modelClasses.ModelPerson;
import modelClasses.ModelUser;
import serviceClasses.RequestLoad;

import javax.xml.crypto.Data;
import java.sql.SQLException;

/**
 * Response Body for Load
 */
public class ResponseLoad {
    private DatabaseDatabase database;
    private DaoUser userDao;
    private DaoPerson personDao;
    private DaoEvent eventDao;

    private Boolean success;
    private String message;

    public ResponseLoad(DatabaseDatabase database) {
        this.database = database;
        this.userDao = database.getUserDao();
        this.personDao = database.getPersonDao();
        this.eventDao = database.getEventsDao();

        setSuccess(false);
        setMessage("Fail");
    }

    public ResponseLoad(RequestLoad requestLoad){
        loadResult(requestLoad);
    }

    public void loadResult(RequestLoad requestLoad) {
        setMessage("Success in load person/ users");
        setSuccess(true);

        try {
            for (ModelUser u : requestLoad.getUsers()) {
                userDao.insert(u, database.getConnection());
            }
        } catch (SQLException e) {
            setMessage("Didnt add user in load to database");
            setSuccess(false);
            e.printStackTrace();
        }

        try {
            for (ModelPerson p : requestLoad.getPersons()) {
                personDao.insert(p,database.getConnection());
            }
        } catch (DatabaseException e) {
            setMessage("Didnt add person in load to database");
            setSuccess(false);
            e.printStackTrace();
        }

        try {
            for (ModelEvent e : requestLoad.getEvents()) {
                eventDao.insert(e,database.getConnection());
            }
        } catch (DatabaseException e) {
            setMessage("Didnt add event in load to database");
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