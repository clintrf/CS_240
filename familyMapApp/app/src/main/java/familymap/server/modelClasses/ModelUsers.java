package familymap.server.modelClasses;

public class ModelUsers{

    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    public ModelUsers(
            String userName,
            String password,
            String email,
            String firstName,
            String lastName,
            String gender,
            String personID){

        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;

    }
    public void setUserName(String userName){ this.userName = userName; }
    public void setPassword(String password){ this.password = password; }
    public void setEmail(String email){ this.email = email; }
    public void setFirstName(String firstName){ this.firstName = firstName; }
    public void setLastName(String lastName){ this.lastName = lastName; }
    public void setGender(String gender){ this.gender = gender; }
    public void setPersonID(String personID){ this.personID = personID; }

    public String getUserName(){ return this.userName; }
    public String getPassword(){ return this.password; }
    public String getEmail(){ return this.email; }
    public String getFirstName(){ return this.firstName; }
    public String getLastName(){ return this.lastName; }
    public String getGender(){ return this.gender; }
    public String getPersonID(){ return this.personID; }
}