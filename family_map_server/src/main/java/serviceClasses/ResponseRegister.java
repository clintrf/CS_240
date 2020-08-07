package main.java.serviceClasses;

import main.java.daoClasses.*;
import main.java.modelClasses.ModelAuthTokens;
import main.java.modelClasses.ModelUsers;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class ResponseRegister{

    private String authToken;
    private String userName;
    private String personID;
    private Boolean success;
    private String message;

    public void setUserName(String userName){ this.userName = userName; }
    public void setAuthToken(String authToken){ this.authToken = authToken; }
    public void setPersonID(String personID){ this.personID = personID; }
    public void setSuccess(Boolean success){ this.success = success; }
    public void setMessage(String message){ this.message = message; }

    public String getAuthToken() { return this.authToken; }
    public String getUserName() { return this.userName; }
    public String getPersonID() { return this.personID; }
    public Boolean getSuccess(){ return this.success; }
    public String getMessage() { return this.message; }


    public static ResponseRegister register(RequestRegister registerRequest, Connection conn) {
        EncoderDecoder coder = new EncoderDecoder();
        ResponseRegister registerResponse = new ResponseRegister();

        DaoUser userDao = new DaoUser(conn);
        DaoAuthToken authTokenDao = new DaoAuthToken(conn);

        ModelUsers userObject;
        ModelAuthTokens authTokenObject;
        try{

            userObject = coder.decodeModelUsers(coder.encodeRequestRegister(registerRequest));
            userObject.setPersonID(getRandomIDNum());
            userDao.insert(userObject);

            authTokenObject = new ModelAuthTokens(
                    getRandomIDNum(),
                    userObject.getUserName(),
                    userObject.getPassword()
            );
            authTokenDao.insert(authTokenObject);

            ResponseFill.fill(userObject.getUserName(),4,conn);

            registerResponse.setAuthToken(authTokenObject.getAuthToken());
            registerResponse.setUserName(userObject.getUserName());
            registerResponse.setPersonID(userObject.getPersonID());
            registerResponse.setSuccess(true);
            return registerResponse;

        }catch(SQLException e){
            registerResponse.setMessage("error");
            registerResponse.setSuccess(false);
            return registerResponse;
        }


    }
    public static String getRandomIDNum(){
        return UUID.randomUUID().toString().replace("-", "").substring(0,8);
    }
}