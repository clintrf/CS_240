package familymap.server.serviceClasses;

import java.sql.Connection;
import java.sql.SQLException;

import familymap.server.daoClasses.DaoAuthToken;
import familymap.server.daoClasses.DaoEvent;
import familymap.server.modelClasses.ModelAuthTokens;
import familymap.server.modelClasses.ModelEvents;

public class ResponseEvent{

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

    public static ResponseEvent event(String authToken, String eventID, Connection conn){
        EncoderDecoder coder = new EncoderDecoder();
        ResponseEvent eventsResponse = new ResponseEvent();

        DaoAuthToken authTokenDao = new DaoAuthToken(conn);
        DaoEvent eventDao = new DaoEvent(conn);

        ModelAuthTokens authTokenModel;
        ModelEvents eventModel;
        try {
            authTokenModel = authTokenDao.getAuthTokenByToken(authToken);
            if (authTokenModel == null){
                throw new SQLException();
            }
            eventModel = eventDao.getEventById(eventID);
            if(eventModel == null){
                throw new SQLException();
            }
            if(!(eventModel.getAssociatedUsername().equals(authTokenModel.getUserName()))){
                throw new SQLException();
            }
            eventsResponse = coder.decodeResponseEvent(coder.encodeModelEvents(eventModel));
            eventsResponse.setSuccess(true);
            return eventsResponse;
        }catch(SQLException ex){
            eventsResponse.setMessage("error");
            eventsResponse.setSuccess(false);
            return eventsResponse;
        }
    }
}