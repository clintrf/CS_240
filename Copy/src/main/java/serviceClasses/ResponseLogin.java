
package main.java.serviceClasses;

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



}