package serviceClasses.resultService;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoUser;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import modelClasses.ModelAuthToken;
import modelClasses.ModelUser;
import serviceClasses.Services;
import serviceClasses.requestService.RequestLogin;

public class ResultsLogin {

    private DaoAuthToken tokenDao;
    private DaoUser userDao;

    private String authToken;
    private String userName;
    private String personId;
    private Boolean success;
    private String message;

    public ResultsLogin(DatabaseDatabase database) {
        this.tokenDao = database.getTokenDao();
        this.userDao = database.getUserDao();

        setAuthToken(null);
        setUserName(null);
        setPersonId(null);
        setSuccess(false);
        setMessage("Fail");
    }

    public void loginResult(RequestLogin request) {

        try { ModelUser userObj = userDao.findUserByUserName(request.getUserName());
            if(!(userObj.getPassword().equals(request.getPassword()))){
                setMessage("Passwords are not equal");
                setSuccess(false);
                return;
            }

            try{ ModelAuthToken tokenObj = tokenDao.findAuthTokenByUserName(userObj.getUserName());
                setAuthToken(Services.getRandomId());
                setUserName(tokenObj.getUserName());
                setPersonId(Services.getRandomId());

                try { tokenDao.insert(new ModelAuthToken(getAuthToken(),tokenObj.getUserName(),tokenObj.getPassword()));
                    setSuccess(true);
                    setMessage("Success");

                } catch (DatabaseException e) {
                    setSuccess(false);
                    setMessage("Error");
                    e.printStackTrace();
                }
            } catch (DatabaseException e) {
                setSuccess(false);
                setMessage("Error");
                e.printStackTrace();
            }
        } catch (Exception e) {
            setSuccess(false);
            setMessage("Error");
            e.printStackTrace();
        }



    }




    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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


    public String getAuthToken() {
        return this.authToken;
    }

    public String getUserName() {
        return this.userName;
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
