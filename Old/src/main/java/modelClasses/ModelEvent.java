package modelClasses;

/**
 * eventModel represents a row in the event table and its variables relate to the columns in the row
 */
public class ModelEvent {

    /**
     * Unique identifier for this event (non-empty string)
     */
    private String eventID;

    /**
     * User (Username) to which this person belongs
     */
    private String associatedUsername;

    /**
     * ID of person to which this event belongs
     */
    private String personID;

    /**
     * Latitude of event’s location
     */
    private Double latitude;

    /**
     * Longitude of event’s location
     */
    private Double longitude;

    /**
     * Country in which event occurred
     */
    private String country;

    /**
     * City in which event occurred
     */
    private String city;

    /**
     * Type of event (birth, baptism, christening, marriage, death, etc.)
     */
    private String eventType;

    /**
     * Year in which event occurred
     */
    private Integer year;


    /**
     * Constructor for eventModel. Inits all user vars
     * @param eventID Unique identifier for this event (non-empty string)
     * @param associatedUsername User (Username) to which this person belongs
     * @param personID ID of person to which this event belongs
     * @param latitude Latitude of event’s location
     * @param longitude Longitude of event’s location
     * @param country Country in which event occurred
     * @param city City in which event occurred
     * @param eventType Type of event (birth, baptism, christening, marriage, death, etc.)
     * @param year Year in which event occurred
     */
    public ModelEvent(String eventID,
                      String associatedUsername,
                      String personID,
                      Double latitude,
                      Double longitude,
                      String country,
                      String city,
                      String eventType,
                      Integer year){
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * Sets the eventId variable
     * @param eventId Unique identifier for this event (non-empty string)
     */
    public void setEventID(String eventId){
        this.eventID = eventId;
    }

    /**
     * Sets the associatedUsername variable
     * @param associatedUsername User (Username) to which this person belongs
     */
    public void setAssociatedUserName(String associatedUsername){
        this.associatedUsername = associatedUsername;
    }

    /**
     * Sets the personID variable
     * @param personID ID of person to which this event belongs
     */
    public void setPersonID(String personID){
        this.personID = personID;
    }

    /**
     * Sets the latitude variable
     * @param latitude Latitude of event’s location
     */
    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }

    /**
     * Sets the longitude variable
     * @param longitude Longitude of event’s location
     */
    public void setLongitude(Double longitude){
        this.longitude = longitude;
    }

    /**
     * Sets the country variable
     * @param country Country in which event occurred
     */
    public void setCountry(String country){
        this.country = country;
    }

    /**
     * Sets the city variable
     * @param city City in which event occurred
     */
    public void setCity(String city){
        this.city = city;
    }

    /**
     * Sets the eventType variable
     * @param eventType Type of event (birth, baptism, christening, marriage, death, etc.)
     */
    public void setEventType(String eventType){
        this.eventType = eventType;
    }

    /**
     * Sets the year variable
     * @param year Year in which event occurred
     */
    public void setYear(Integer year){
        this.year = year;
    }


    /**
     * Gets the eventId variable
     * @return eventId
     */
    public String getEventID(){
        return this.eventID;
    }

    /**
     * Gets the associatedUserName variable
     * @return associatedUserName
     */
    public String getAssociatedUsername(){
        return this.associatedUsername;
    }

    /**
     * Gets the personId variable
     * @return personId
     */
    public String getPersonID(){
        return this.personID;
    }

    /**
     * Gets the latitude variable
     * @return latitude
     */
    public Double getLatitude(){
        return this.latitude;
    }

    /**
     * Gets the longitude variable
     * @return longitude
     */
    public Double getLongitude(){
        return this.longitude;
    }

    /**
     * Gets the country variable
     * @return country
     */
    public String getCountry(){
        return this.country;
    }

    /**
     * Gets the city variable
     * @return city
     */
    public String getCity(){
        return this.city;
    }

    /**
     * Gets the eventType variable
     * @return eventType
     */
    public String getEventType(){
        return this.eventType;
    }

    /**
     * Gets the year variable
     * @return year
     */
    public Integer getYear(){
        return this.year;
    }
}
