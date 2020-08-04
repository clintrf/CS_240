package serviceClasses.resultService;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoUser;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import handlerClasses.EncoderDecoder;
import modelClasses.ModelAuthToken;
import modelClasses.ModelUser;
import serviceClasses.Services;
import serviceClasses.requestService.RequestRegister;

/**
 * URL Path: /user/register
 * Description: Responce Body
 */
public class ResultsRegister {
    private EncoderDecoder coder;
    private DaoUser userDao;
    private DaoAuthToken tokenDao;

    private String userName;
    private String authToken;
    private String personId;
    private Boolean success;
    private String message;


    public ResultsRegister(DatabaseDatabase database) {
        this.coder = new EncoderDecoder();
        this.userDao = database.getUserDao();
        this.tokenDao = database.getTokenDao();

        setUserName(null);
        setAuthToken(null);
        setPersonId(null);
        setSuccess(false);
        setMessage("Fail");
    }

    public ResultsRegister(RequestRegister request){
        registerResult(request);
    }

    public void registerResult(RequestRegister request) {
        setAuthToken(Services.getRandomId());
        setPersonId(Services.getRandomId());

        ModelUser user = coder.decodeToModelUser(coder.encode(request));
        user.setPersonID(getPersonId());

        setUserName(user.getUserName());

        ModelAuthToken authToken = new ModelAuthToken(
                getAuthToken(),
                user.getUserName(),
                user.getPassword()
        );

        try{
            userDao.insert(user);
            tokenDao.insert(authToken);
            setMessage("added to database in ResultsRegister service");
            setSuccess(true);
        } catch (DatabaseException e) {
            setUserName(null);
            setAuthToken(null);
            setPersonId(null);
            setMessage("Could not add to database in ResultsRegister service");
            setSuccess(false);
            e.printStackTrace();
        }
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
