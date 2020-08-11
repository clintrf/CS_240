package server.serviceClasses;

import server.daoClasses.DaoAuthToken;
import server.daoClasses.DaoUser;
import server.modelClasses.ModelAuthTokens;
import server.modelClasses.ModelUsers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class ResponseLogin{

    private String userName;
    private String authToken;
    private String personID;
    private String message;
    private Boolean success;

    public void setUserName(String userName){ this.userName = userName; }
    public void setAuthToken(String authToken){ this.authToken = authToken; }
    public void setPersonID(String personID){ this.personID = personID; }
    public void setMessage(String message){ this.message = message; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getUserName() { return this.userName; }
    public String getAuthToken() { return this.authToken; }
    public String getPersonID() { return this.personID; }
    public String getMessage() { return this.message; }
    public Boolean getSuccess() { return this.success; }


    public static ResponseLogin login(RequestLogin loginRequest, Connection conn) {
        ResponseLogin loginResponse = new ResponseLogin();

        DaoAuthToken authTokenDao = new DaoAuthToken(conn);
        DaoUser userDao = new DaoUser(conn);

        ModelUsers userModel;
        ModelAuthTokens authTokenModel;

        try {
            userModel = userDao.getUserByUsername(loginRequest.getUserName());
            if (userModel ==  null){
                throw new SQLException();
            }
            if (!(userModel.getPassword().equals(loginRequest.getPassword()))) {
                throw new SQLException();
            }

            authTokenModel = new ModelAuthTokens(
                    getRandomIDNum(),
                    userModel.getUserName(),
                    userModel.getPassword()
            );
            authTokenDao.insert(authTokenModel);

            loginResponse.setPersonID(userModel.getPersonID());
            loginResponse.setUserName(userModel.getUserName());
            loginResponse.setAuthToken(authTokenModel.getAuthToken());
            loginResponse.setSuccess(true);
            return loginResponse;

        } catch (Exception ex) {
            loginResponse.setMessage("error");
            loginResponse.setSuccess(false);
            return loginResponse;
        }

    }
    public static String getRandomIDNum(){
        return UUID.randomUUID().toString().replace("-", "").substring(0,8);
    }
}