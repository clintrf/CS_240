package serviceClasses.resultService;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoUser;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import modelClasses.ModelAuthToken;
import modelClasses.ModelUser;
import serviceClasses.Services;
import serviceClasses.requestService.RequestLogin;
import serviceClasses.requestService.RequestRegister;

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

    public ResultsLogin(RequestLogin request){
        loginResult(request);
    }

    public void loginResult(RequestLogin request) {

        try {
            ModelUser userObj = userDao.findUserByUserName(request.getUserName());
            if(userObj.getUserName() == null){
                setAuthToken(null);
                setUserName(null);
                setPersonId(null);
                setMessage("No user");
                setSuccess(false);
                return;
            }

            if(!(userObj.getPassword().equals(request.getPassword()))){
                setAuthToken(null);
                setUserName(null);
                setPersonId(null);
                setMessage("Passwords are not equal");
                setSuccess(false);
                return;
            }


            ModelAuthToken tokenObj = tokenDao.findAuthTokenByUserName(userObj.getUserName());

            setAuthToken(Services.getRandomId());
            setUserName(userObj.getUserName());
            setPersonId(Services.getRandomId());

            tokenDao.insert(new ModelAuthToken(getAuthToken(),getUserName(),userObj.getPassword()));
            setSuccess(true);
            setMessage("Success");

        } catch (DatabaseException e) {
            setAuthToken(null);
            setUserName(null);
            setPersonId(null);
            setMessage("Error Database");
            setSuccess(false);
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
