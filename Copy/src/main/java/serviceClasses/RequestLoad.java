
package main.java.serviceClasses;

import main.java.modelClasses.ModelEvents;
import main.java.modelClasses.ModelPersons;
import main.java.modelClasses.ModelUsers;

import java.util.ArrayList;


public class RequestLoad{

    public ArrayList<ModelUsers> users;
    public ArrayList<ModelPersons> people;
    public ArrayList<ModelEvents> events;

    public void setUsers(ArrayList<ModelUsers> users) { this.users = users; }
    public void setPeople(ArrayList<ModelPersons> people) { this.people = people; }
    public void setEvents(ArrayList<ModelEvents> events) { this.events = events; }


    public ArrayList<ModelUsers> getUsers(){ return users; }
    public ArrayList<ModelPersons> getPeople(){ return people; }
    public ArrayList<ModelEvents> getEvents(){ return events; }

}