package serviceClasses;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoEvent;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import modelClasses.ModelAuthToken;
import modelClasses.ModelEvent;

import java.sql.SQLException;

public class ResponseEvent {
    private DatabaseDatabase database;
    private DaoAuthToken tokenDao;
    private DaoEvent eventDao;

    private String eventId;
    private String associatedUserName;
    private String personId;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String eventType;
    private Integer year;
    private Boolean success;
    private String message;

    public ResponseEvent(DatabaseDatabase database){
        this.database = database;
        this.tokenDao = database.getTokenDao();
        this.eventDao = database.getEventsDao();

        setEventId(null);
        setAssociatedUserName(null);
        setPersonId(null);
        setLatitude(null);
        setLongitude(null);
        setCountry(null);
        setCity(null);
        setEventType(null);
        setYear(null);
        setSuccess(false);
        setMessage("fail");
    }

    public ResponseEvent(String authToken, String eventId){
        eventResult(authToken, eventId);
    }

    public void eventResult(String authToken, String eventId) {
        try {
            ModelAuthToken tokenObj = tokenDao.getAuthTokenByToken(authToken, database.getConnection());
            if (tokenObj.getUserName() == null){
                setEventId(null);
                setAssociatedUserName(null);
                setPersonId(null);
                setLatitude(null);
                setLongitude(null);
                setCountry(null);
                setCity(null);
                setEventType(null);
                setYear(null);
                setMessage("invalid auth token");
                setSuccess(false);
                return;
            }
            ModelEvent eventObj = eventDao.findEventById(eventId, database.getConnection());
            if(eventObj.getEventID() == null) {
                setEventId(null);
                setAssociatedUserName(null);
                setPersonId(null);
                setLatitude(null);
                setLongitude(null);
                setCountry(null);
                setCity(null);
                setEventType(null);
                setYear(null);
                setMessage("event not in database");
                setSuccess(false);
                return;
            }
            if(!(eventObj.getAssociatedUsername().equals(tokenObj.getUserName()))){
                setEventId(null);
                setAssociatedUserName(null);
                setPersonId(null);
                setLatitude(null);
                setLongitude(null);
                setCountry(null);
                setCity(null);
                setEventType(null);
                setYear(null);
                setMessage("Requested event does not belong to this user");
                setSuccess(false);
                return;
            }
            setEventId(eventObj.getEventID());
            setAssociatedUserName(eventObj.getAssociatedUsername());
            setPersonId(eventObj.getPersonID());
            setLatitude(eventObj.getLatitude());
            setLongitude(eventObj.getLongitude());
            setCountry(eventObj.getCountry());
            setCity(eventObj.getCity());
            setEventType(eventObj.getEventType());
            setYear(eventObj.getYear());
            setMessage("Success");
            setSuccess(true);

        } catch (DatabaseException | SQLException e) {
            setEventId(null);
            setAssociatedUserName(null);
            setPersonId(null);
            setLatitude(null);
            setLongitude(null);
            setCountry(null);
            setCity(null);
            setEventType(null);
            setYear(null);
            setMessage("Error Database");
            setSuccess(false);
            e.printStackTrace();
        }
    }



    public void setAssociatedUserName(String associatedUserName) {
        this.associatedUserName = associatedUserName;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public String getEventId() {
        return this.eventId;
    }

    public String getPersonId() {
        return this.personId;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public String getCountry() {
        return this.country;
    }

    public String getCity() {
        return this.city;
    }

    public String getEventType() {
        return this.eventType;
    }

    public Integer getYear() {
        return this.year;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }
}
