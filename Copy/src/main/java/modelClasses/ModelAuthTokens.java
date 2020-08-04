
package main.java.modelClasses;

public class ModelAuthTokens{
    private String authToken;
    private String userName;
    private String password;

    public ModelAuthTokens( String authToken, String userName, String password){
        this.userName = userName;
        this.authToken = authToken;
        this.password = password;

    }
    public void setUserName(String userName){ this.userName = userName; }
    public void setAuthToken(String authToken){ this.authToken = authToken; }
    public void setPassword(String password){ this.password = password; }

    public String getUserName(){ return this.userName; }
    public String getAuthToken(){ return this.authToken; }
    public String getPassword(){ return  this.password; }
}