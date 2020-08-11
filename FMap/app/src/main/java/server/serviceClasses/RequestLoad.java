package server.serviceClasses;

import java.util.ArrayList;

import server.modelClasses.ModelEvents;
import server.modelClasses.ModelPersons;
import server.modelClasses.ModelUsers;


public class RequestLoad{

    public ArrayList<ModelUsers> users;
    public ArrayList<ModelPersons> persons;
    public ArrayList<ModelEvents> events;

    public void setUsers(ArrayList<ModelUsers> users) { this.users = users; }
    public void setPeople(ArrayList<ModelPersons> persons) { this.persons = persons; }
    public void setEvents(ArrayList<ModelEvents> events) { this.events = events; }


    public ArrayList<ModelUsers> getUsers(){ return users; }
    public ArrayList<ModelPersons> getPeople(){ return persons; }
    public ArrayList<ModelEvents> getEvents(){ return events; }

}