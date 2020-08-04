package serviceClasses;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoEvent;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import modelClasses.ModelAuthToken;
import modelClasses.ModelEvent;

import java.sql.SQLException;

public class ResponseEvent {
    private String associatedUsername;
    private String eventID;
    private String personID;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String eventType;
    private Integer year;
    private String message;
    private Boolean success;

    public void setEventID(String eventID){ this.eventID = eventID; }
    public void setAssociatedUsername(String associatedUsername){ this.associatedUsername = associatedUsername; }
    public void setPersonID(String personID){ this.personID = personID; }
    public void setLatitude(Double latitude){ this.latitude = latitude; }
    public void setLongitude(Double longitude){ this.longitude = longitude; }
    public void setCountry(String country){ this.country = country; }
    public void setCity(String city){ this.city = city; }
    public void setEventType(String eventType){ this.eventType = eventType; }
    public void setYear(int year){ this.year = year; }
    public void setMessage(String message){ this.message = message; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getEventID() { return this.eventID; }
    public String getAssociatedUsername() { return this.associatedUsername; }
    public String getPersonID() { return this.personID; }
    public Double getLatitude() { return this.latitude; }
    public Double getLongitude() { return this.longitude; }
    public String getCountry() { return this.country; }
    public String getCity() { return this.city; }
    public String getEventType() { return this.eventType; }
    public Integer getYear() { return this.year; }
    public String getMessage() { return this.message; }
    public Boolean getSuccess() { return this.success; }

}
