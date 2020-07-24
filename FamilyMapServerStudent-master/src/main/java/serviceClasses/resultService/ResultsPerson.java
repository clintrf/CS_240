package serviceClasses.resultService;

public class ResultsPerson {

    private String associatedUserName;
    private String personId;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherId;
    private String motherId;
    private String spouseId;
    private Boolean success;
    private String message;

    public ResultsPerson(){
        this.associatedUserName = null;
        this.personId = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.fatherId = null;
        this.motherId = null;
        this.spouseId = null;
        this.success = false;
        this.message = "Error";
    }

    public void setAssociatedUserName(String associatedUserName) {
        this.associatedUserName = associatedUserName;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    public void setSpouseId(String spouseId) {
        this.spouseId = spouseId;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getAssociatedUserName() {
        return this.associatedUserName;
    }

    public String getPersonId() {
        return this.personId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getGender() {
        return this.gender;
    }

    public String getFatherId() {
        return this.fatherId;
    }

    public String getMotherId() {
        return this.motherId;
    }

    public String getSpouseId() {
        return this.spouseId;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }
}
