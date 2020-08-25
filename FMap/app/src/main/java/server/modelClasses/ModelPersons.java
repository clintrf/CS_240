package server.modelClasses;

public class ModelPersons{

    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    public ModelPersons(
            String personID,
            String associatedUsername,
            String firstName,
            String lastName,
            String gender,
            String fatherID,
            String motherID,
            String spouseID
    ){
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;

    }

    public ModelPersons() {

    }

    public void setPersonID(String personID){ this.personID = personID; }
    public void setAssociatedUsername(String associatedUsername){ this.associatedUsername = associatedUsername; }
    public void setFirstName(String firstName){ this.firstName = firstName; }
    public void setLastName(String lastName){ this.lastName = lastName; }
    public void setGender(String gender){ this.gender = gender; }
    public void setFatherID(String fatherID){ this.fatherID = fatherID; }
    public void setMotherID(String motherID){ this.motherID = motherID; }
    public void setSpouseID(String spouseID){ this.spouseID = spouseID; }

    public String getPersonID(){ return this.personID; }
    public String getAssociatedUsername(){ return this.associatedUsername; }
    public String getFirstName(){ return this.firstName; }
    public String getLastName(){ return this.lastName; }
    public String getGender(){ return this.gender; }
    public String getFatherID(){ return this.fatherID; }
    public String getMotherID(){ return this.motherID; }
    public String getSpouseID(){ return this.spouseID; }

    public String getDescription() {
        String out = new String(firstName + " " + lastName);
        return out;
    }
}