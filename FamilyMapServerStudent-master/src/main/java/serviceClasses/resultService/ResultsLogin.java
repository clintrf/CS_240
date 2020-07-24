package serviceClasses.resultService;

public class ResultsLogin {
    private String authToken;
    private String userName;
    private String personId;
    private Boolean success;
    private String message;

    public ResultsLogin(){
        this.authToken = null;
        this.userName = null;
        this.personId = null;
        this.success = false;
        this.message = "Error";
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
