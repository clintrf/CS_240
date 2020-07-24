package serviceClasses.resultService;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoUser;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import modelClasses.ModelAuthToken;
import modelClasses.ModelUser;
import serviceClasses.requestService.RequestRegister;

import java.sql.Connection;
import java.util.UUID;

/**
 * URL Path: /user/register
 * Description: Responce Body
 */
public class ResultsRegister {
    private String userName;
    private String authToken;
    private String personId;
    private Boolean success;
    private String message;


    public ResultsRegister(ModelUser user) throws DatabaseException {
        this.userName = user.getUserName();
        this.authToken = UUID.randomUUID().toString().substring(0, 8);
        this.personId = UUID.randomUUID().toString().substring(0, 8);
        this.success = false;
        this.message = "Error";

        DatabaseDatabase database = new DatabaseDatabase();
        Connection conn = database.openConnection();

        ModelAuthToken authToken = new ModelAuthToken(this.authToken, user.getUserName(), user.getPassword());

        DaoUser userDao = new DaoUser(conn);
        DaoAuthToken tokenDao = new DaoAuthToken(conn);

        try{
            userDao.insert(user);
            tokenDao.insert(authToken);
            this.success = true;
        } catch (DatabaseException e) {
            this.message = "Could not add to database in ResultsRegister service";
            this.success = false;
            e.printStackTrace();
        }


        database.closeConnection(false);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getUserName() {
        return this.userName;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public String getPersonId() {
        return this.personId;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }
}
