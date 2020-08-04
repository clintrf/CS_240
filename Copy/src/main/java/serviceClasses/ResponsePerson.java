
package main.java.serviceClasses;

public class ResponsePerson{

    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;
    private String message;
    private Boolean success;

    public void setPersonID(String personID){ this.personID = personID; }
    public void setAssociatedUsername(String associatedUsername){ this.associatedUsername = associatedUsername; }
    public void setFirstName(String firstName){ this.firstName = firstName; }
    public void setLastName(String lastName){ this.lastName = lastName; }
    public void setGender(String gender){ this.gender = gender; }
    public void setFatherID(String fatherID){ this.fatherID = fatherID; }
    public void setMotherID(String motherID){ this.motherID = motherID; }
    public void setSpouseID(String spouseID){ this.spouseID = spouseID; }
    public void setMessage(String message){ this.message = message; }
    public void setSuccess(Boolean success) { this.success = success; }


    public String getAssociatedUsername() { return this.associatedUsername; }
    public String getPersonID() { return this.personID; }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public String getGender() { return this.gender; }
    public String getFatherID() { return this.fatherID; }
    public String getMotherID() { return this.motherID; }
    public String getSpouseID() { return this.spouseID; }
    public String getMessage() { return this.message; }
    public Boolean getSuccess() { return this.success; }
}