package serviceClasses;

import modelClasses.*;

import java.util.ArrayList;

public class RequestLoad {

    public ArrayList<ModelUser> users;
    public ArrayList<ModelPerson> persons;
    public ArrayList<ModelEvent> events;

    public void setUsers(ArrayList<ModelUser> users) { this.users = users; }
    public void setPeople(ArrayList<ModelPerson> persons) { this.persons = persons; }
    public void setEvents(ArrayList<ModelEvent> events) { this.events = events; }


    public ArrayList<ModelUser> getUsers(){ return users; }
    public ArrayList<ModelPerson> getPeople(){ return persons; }
    public ArrayList<ModelEvent> getEvents(){ return events; }
}
