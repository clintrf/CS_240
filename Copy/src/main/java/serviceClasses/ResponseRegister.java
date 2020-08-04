
package main.java.serviceClasses;

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

}
