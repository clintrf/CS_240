
package main.java.modelClasses;

public class ModelEvents{

    private String associatedUsername;
    private String eventID;
    private String personID;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String eventType;
    private Integer year;

    public ModelEvents(String eventID,
                       String associatedUsername,
                       String personID,
                       Double latitude,
                       Double longitude,
                       String country,
                       String city,
                       String eventType,
                       Integer year){

        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;

    }

    public void setNull(){
        latitude = null;
        longitude = null;
        year = null;
    }

    public void setAssociatedUsername(String associatedUsername){ this.associatedUsername = associatedUsername; }
    public void setEventID(String eventID){ this.eventID = eventID; }
    public void setPersonID(String personID){ this.personID = personID; }
    public void setLatitude(Double latitude){ this.latitude = latitude; }
    public void setLongitude(double longitude){ this.longitude = longitude; }
    public void setCountry(String country){ this.country = country; }
    public void setCity(String city){ this.city = city; }
    public void setEventType(String eventType){ this.eventType = eventType; }
    public void setYear(int year){ this.year = year; }



    public String getAssociatedUsername(){ return this.associatedUsername; }
    public String getEventID(){ return this.eventID; }
    public String getPersonID(){ return this.personID; }
    public double getLatitude(){ return this.latitude; }
    public double getLongitude(){ return this.longitude; }
    public String getCountry(){ return this.country; }
    public String getCity(){ return this.city; }
    public String getEventType(){ return this.eventType; }
    public int getYear(){ return this.year; }
}