package modelClasses;

/**
 * personModel represents a row in the persons table and its variables relate to the columns in the row
 */
public class ModelPerson {

    /**
     * Unique identifier for this person (non-empty string)
     */
    private String personID;

    /**
     * User (Username) to which this person belongs
     */
    private String associatedUsername;

    /**
     * Person’s first name (non-empty string)
     */
    private String firstName;

    /**
     * Person’s last name (non-empty string)
     */
    private String lastName;

    /**
     * Person’s gender (string: “f” or “m”)
     */
    private String gender;

    /**
     * Person ID of person’s father (possibly null)
     */
    private String fatherID;

    /**
     * Person ID of person’s mother (possibly null)
     */
    private String motherID;

    /**
     * Person ID of person’s spouse (possibly null)
     */
    private String spouseID;


    /**
     * Constructor for personModel. Inits all user vars
     * @param personId Unique identifier for this person (non-empty string)
     * @param associatedUserName Username: User (Username) to which this person belongs
     * @param firstName Person’s first name (non-empty string)
     * @param lastName Person’s last name (non-empty string)
     * @param gender Person’s gender (string: “f” or “m”)
     * @param fatherId Person ID of person’s father (possibly null)
     * @param motherId Person ID of person’s mother (possibly null)
     * @param spouseId Person ID of person’s spouse (possibly null)
     */
    public ModelPerson(String personId,
                       String associatedUserName,
                       String firstName,
                       String lastName,
                       String gender,
                       String fatherId,
                       String motherId,
                       String spouseId){
        this.personID = personId;
        this.associatedUsername = associatedUserName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherId;
        this.motherID = motherId;
        this.spouseID = spouseId;
    }

    /**
     * Sets the personId variable
     * @param personID Unique identifier for this person (non-empty string)
     */
    public void setPersonID(String personID){
        this.personID = personID;
    }

    /**
     * Sets the associatedUserName variable
     * @param associatedUsername Username: User (Username) to which this person belongs
     */
    public void setAssociatedUsername(String associatedUsername){
        this.associatedUsername = associatedUsername;
    }

    /**
     * Sets the firstName variable
     * @param firstName Person’s first name (non-empty string)
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    /**
     * Sets the lastName variable
     * @param lastName Person’s last name (non-empty string)
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
     * Sets the gender variable
     * @param gender Person’s gender (string: “f” or “m”)
     */
    public void setGender(String gender){
        this.gender = gender;
    }

    /**
     * Sets the fatherId variable
     * @param fatherID Person ID of person’s father (possibly null)
     */
    public void setFatherID(String fatherID){
        this.fatherID = fatherID;
    }

    /**
     * Sets the motherId variable
     * @param motherID Person ID of person’s mother (possibly null)
     */
    public void setMotherID(String motherID){
        this.motherID = motherID;
    }

    /**
     * Sets the spouseId variable
     * @param spouseID Person ID of person’s spouse (possibly null)
     */
    public void setSpouseID(String spouseID){
        this.spouseID = spouseID;
    }


    /**
     * Gets the personId variable
     * @return personId
     */
    public String getPersonID(){
        return this.personID;
    }

    /**
     * Gets the associatedUserName variable
     * @return associatedUserName
     */
    public String getAssociatedUsername(){
        return this.associatedUsername;
    }

    /**
     * Gets the firstName variable
     * @return firstName
     */
    public String getFirstName(){
        return this.firstName;
    }

    /**
     * Gets the lastName variable
     * @return lastName
     */
    public String getLastName(){
        return this.lastName;
    }

    /**
     * Gets the gender variable
     * @return gender
     */
    public String getGender(){
        return this.gender;
    }

    /**
     * Gets the fatherId variable
     * @return fatherId
     */
    public String getFatherID(){
        return this.fatherID;
    }

    /**
     * Gets the motherId variable
     * @return motherId
     */
    public String getMotherID(){
        return this.motherID;
    }

    /**
     * Gets the spouseId variable
     * @return spouseId
     */
    public String getSpouseID(){
        return this.spouseID;
    }
}
