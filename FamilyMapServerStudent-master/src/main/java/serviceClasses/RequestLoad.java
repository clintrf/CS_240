package serviceClasses;

import modelClasses.*;

import java.util.ArrayList;

public class RequestLoad {
    private ArrayList<ModelUser> users;
    private ArrayList<ModelPerson> persons;
    private ArrayList<ModelEvent> events;

    public RequestLoad(){
        this.users = new ArrayList<ModelUser>();
        this.persons = new ArrayList<ModelPerson>();
        this.events = new ArrayList<ModelEvent>();
    }

    public void setUsers(ArrayList<ModelUser> users) {
        this.users = users;
    }

    public void setPersons(ArrayList<ModelPerson> persons) {
        this.persons = persons;
    }

    public void setEvents(ArrayList<ModelEvent> events) {
        this.events = events;
    }


    public ArrayList<ModelUser> getUsers() {
        return this.users;
    }

    public ArrayList<ModelPerson> getPersons() {
        return this.persons;
    }

    public ArrayList<ModelEvent> getEvents() {
        return this.events;
    }
}
