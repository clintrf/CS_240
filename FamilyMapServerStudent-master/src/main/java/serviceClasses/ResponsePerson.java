package serviceClasses;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoPerson;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import handlerClasses.EncoderDecoder;
import modelClasses.ModelAuthToken;
import modelClasses.ModelPerson;

import java.sql.Connection;
import java.sql.SQLException;

public class ResponsePerson {

    private EncoderDecoder coder;
    private DatabaseDatabase database;
    private Connection conn;
    private DaoAuthToken tokenDao;
    private DaoPerson personDao;


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

    public ResponsePerson(DatabaseDatabase database)  {
        this.coder = new EncoderDecoder();
        this.database = database;
        this.conn = database.getConnection();
        this.tokenDao = database.getTokenDao();
        this.personDao = database.getPersonDao();


        setSuccess(false);
        setMessage("Fail");
    }

    public ResponsePerson(String auth_token, String personId) {
    personResult(auth_token,personId);
    }

    public void personResult(String auth_token, String personId) {
        try {
            ModelAuthToken tokenObj = tokenDao.getAuthTokenByToken(auth_token,database.getConnection());
            if(tokenObj.getUserName() == null){
                setAssociatedUserName(null);
                setPersonId(null);
                setFirstName(null);
                setGender(null);
                setFatherId(null);
                setMotherId(null);
                setSpouseId(null);
                setMessage("token not found/ invalid token");
                setSuccess(false);
                return;
            }
            ModelPerson personObj = personDao.findPersonById(personId, database.getConnection());
            if(personObj.getPersonID() == null) {
                setAssociatedUserName(null);
                setPersonId(null);
                setFirstName(null);
                setGender(null);
                setFatherId(null);
                setMotherId(null);
                setSpouseId(null);
                setMessage("Cant find person");
                setSuccess(false);
                return;
            }
            if (!(personObj.getAssociatedUsername().equals(tokenObj.getUserName()))) {
                setAssociatedUserName(null);
                setPersonId(null);
                setFirstName(null);
                setGender(null);
                setFatherId(null);
                setMotherId(null);
                setSpouseId(null);
                setMessage("permission denied");
                setSuccess(false);
                return;
            }
            //ResultsPerson response = coder.decodeToResultsPerson(coder.encode(personObj));
            setAssociatedUserName(personObj.getAssociatedUsername());
            setPersonId(personObj.getPersonID());
            setFirstName(personObj.getFirstName());
            setLastName(personObj.getLastName());
            setGender(personObj.getGender());
            setFatherId(personObj.getFatherID());
            setMotherId(personObj.getMotherID());
            setSpouseId(personObj.getSpouseID());
            setMessage("success in results person");
            setSuccess(true);

        } catch (DatabaseException | SQLException e) {
            setAssociatedUserName(null);
            setPersonId(null);
            setFirstName(null);
            setGender(null);
            setFatherId(null);
            setMotherId(null);
            setSpouseId(null);
            setMessage("Error Database");
            setSuccess(false);
            e.printStackTrace();
        }
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
