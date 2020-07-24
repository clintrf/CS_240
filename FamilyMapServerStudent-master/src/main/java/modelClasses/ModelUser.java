package modelClasses;

/**
 * userModel represents a row in the user table and its variables relate to the columns in the row
 */
public class ModelUser {

    /**
     * Unique user name (non-empty string)
     */
    private String userName;

    /**
     * User’s password (non-empty string)
     */
    private String password;

    /**
     * User’s email address (non-empty string)
     */
    private String email;

    /**
     * User’s first name (non-empty string)*
     */
    private String firstName;

    /**
     * User’s last name (non-empty string)*
     */
    private String lastName;

    /**
     * User’s gender (string: “f” or “m”)*
     */
    private String gender;

    /**
     * Unique Person ID assigned to this user’s generated Person object -
     * see Family History Information section for details (non-empty string)
     */
    private String personId;

    /**
     * Constructor for userModel. Inits all user vars
     * @param userName Unique user name (non-empty string)
     * @param password User’s password (non-empty string)
     * @param email User’s email address (non-empty string)
     * @param firstName User’s first name (non-empty string)
     * @param lastName User’s last name (non-empty string)
     * @param gender User’s gender (string: “f” or “m”)
     * @param personId Unique Person ID assigned to this user’s generated Person object
     */
    public ModelUser(String userName,
                     String password,
                     String email,
                     String firstName,
                     String lastName,
                     String gender,
                     String personId){
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personId = personId;

    }

    /**
     * Sets userName variable
     * @param userName Unique user name (non-empty string)
     */
    public void setUserName(String userName){
        this.userName = userName;
    }

    /**
     * Sets password variable
     * @param password User’s password (non-empty string)
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * Sets email variable
     * @param email User’s email address (non-empty string)
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Sets firstName variable
     * @param firstName User’s first name (non-empty string)
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    /**
     * Sets lastName variable
     * @param lastName User’s last name (non-empty string)
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
     * Sets gender variable
     * @param gender User’s gender (string: “f” or “m”)
     */
    public void setGender(String gender){
        this.gender = gender;
    }

    /**
     * Sets personId variable
     * @param personId Unique Person ID assigned to this user’s generated Person object
     */
    public void setPersonId(String personId){
        this.personId = personId;
    }


    /**
     * Returns userName variable
     * @return userName
     */
    public String getUserName(){
        return this.userName;
    }

    /**
     * Returns password variable
     * @return password
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * Returns email variable
     * @return email
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * Returns firstName variable
     * @return firstName
     */
    public String getFirstName(){
        return this.firstName;
    }

    /**
     * Returns lastName variable
     * @return lastName
     */
    public String getLastName(){
        return this.lastName;
    }

    /**
     * Returns gender variable
     * @return gender
     */
    public String getGender(){
        return this.gender;
    }

    /**
     * Returns personId variable
     * @return personId
     */
    public String getPersonId(){
        return this.personId;
    }
}
