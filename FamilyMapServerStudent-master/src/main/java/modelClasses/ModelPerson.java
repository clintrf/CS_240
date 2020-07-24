package modelClasses;

/**
 * personModel represents a row in the persons table and its variables relate to the columns in the row
 */
public class ModelPerson {

    /**
     * Unique identifier for this person (non-empty string)
     */
    private String personId;

    /**
     * User (Username) to which this person belongs
     */
    private String associatedUserName;

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
    private String fatherId;

    /**
     * Person ID of person’s mother (possibly null)
     */
    private String motherId;

    /**
     * Person ID of person’s spouse (possibly null)
     */
    private String spouseId;


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
        this.personId = personId;
        this.associatedUserName = associatedUserName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherId = fatherId;
        this.motherId = motherId;
        this.spouseId = spouseId;
    }

    /**
     * Sets the personId variable
     * @param personId Unique identifier for this person (non-empty string)
     */
    public void setPersonId(String personId){
        this.personId = personId;
    }

    /**
     * Sets the associatedUserName variable
     * @param associatedUserName Username: User (Username) to which this person belongs
     */
    public void setAssociatedUserName(String associatedUserName){
        this.associatedUserName = associatedUserName;
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
     * @param fatherId Person ID of person’s father (possibly null)
     */
    public void setFatherId(String fatherId){
        this.fatherId = fatherId;
    }

    /**
     * Sets the motherId variable
     * @param motherId Person ID of person’s mother (possibly null)
     */
    public void setMotherId(String motherId){
        this.motherId = motherId;
    }

    /**
     * Sets the spouseId variable
     * @param spouseId Person ID of person’s spouse (possibly null)
     */
    public void setSpouseId(String spouseId){
        this.spouseId = spouseId;
    }


    /**
     * Gets the personId variable
     * @return personId
     */
    public String getPersonId(){
        return this.personId;
    }

    /**
     * Gets the associatedUserName variable
     * @return associatedUserName
     */
    public String getAssociatedUserName(){
        return this.associatedUserName;
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
    public String getFatherId(){
        return this.fatherId;
    }

    /**
     * Gets the motherId variable
     * @return motherId
     */
    public String getMotherId(){
        return this.motherId;
    }

    /**
     * Gets the spouseId variable
     * @return spouseId
     */
    public String getSpouseId(){
        return this.spouseId;
    }
}
