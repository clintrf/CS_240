package familymap.client.Model;

import familymap.server.modelClasses.ModelEvents;
import familymap.server.modelClasses.ModelPersons;

public class DisplayObj {
    String text;
    String type;
    String IdOfLine;

    public DisplayObj(String topRow, String type, String Id){
        this.text = topRow;
        this.type = type;
        this.IdOfLine = Id;
    }



    public String getLineText(){
        return text;
    }

    public String getLineType(){
        return type;
    }

    public String getLinePersonID() {
        return IdOfLine;
    }


    private String gender;
    private String relation;
    private String eventDetails;
    private String name;
    private String eventType;
    private Integer year;
    private ModelEvents event;
    private ModelPersons person;


    public DisplayObj(ModelPersons person) {
        this.person = person;
        setName(person.getFirstName() + " " + person.getLastName());
        setGender(person.getGender());

    }

    public DisplayObj(ModelEvents event) {
        this.event = event;
        setName(DataCache.getInstance().getPersonByEvent(event).getFirstName() + " " + DataCache.getInstance().getPersonByEvent(event).getLastName());
        setEventDetails(DataCache.getInstance().getEventInfo(event));
        setYear(event.getYear());
        setEventType(event.getEventType());
    }

    public ModelPersons getPerson() {
        return person;
    }

    public ModelEvents getEvent() {
        return event;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}