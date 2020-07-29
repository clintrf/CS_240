package serviceClasses.resultService;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoPerson;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import handlerClasses.EncoderDecoder;
import handlerClasses.Handler;
import modelClasses.ModelAuthToken;
import modelClasses.ModelPerson;

import java.sql.Connection;

public class ResultsPerson {

    private EncoderDecoder coder;
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

    public ResultsPerson(DatabaseDatabase database) {
        this.coder = new EncoderDecoder();
        this.tokenDao = database.getTokenDao();
        this.personDao = database.getPersonDao();

        setAssociatedUserName(null);
        setPersonId(null);
        setFirstName(null);
        setGender("m");
        setFatherId(null);
        setMotherId(null);
        setSpouseId(null);
        setSuccess(false);
        setMessage("Fail");
    }

    public void personResult(String auth_token, String personId) {
        try {
            ModelAuthToken tokenObj = tokenDao.findAuthTokenByToken(auth_token);
            try { ModelPerson personObj = personDao.findPersonById(personId);
                if(!(personObj.getAssociatedUserName().equals(tokenObj.getUserName()))){
                    setMessage("permission denied");
                    setSuccess(false);
                    return;
                }
                setAssociatedUserName(personObj.getAssociatedUserName());
                setPersonId(personObj.getPersonId());
                setFirstName(personObj.getFirstName());
                setLastName(personObj.getLastName());
                setGender(personObj.getGender());
                setFatherId(personObj.getFatherId());
                setMotherId(personObj.getMotherId());
                setSpouseId(personObj.getSpouseId());
                setMessage("success in results person");
                setSuccess(true);
            } catch (DatabaseException e) {
                setMessage("PersonId not found");
                setSuccess(false);
                e.printStackTrace();
            }
        } catch (DatabaseException e) {
            setMessage("token not found/ invalid token");
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
