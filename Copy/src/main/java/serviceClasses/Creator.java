package main.java.serviceClasses;


import java.util.*;
import java.sql.*;
import java.lang.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

import com.google.gson.Gson;
import main.java.dataAccessClasses.DaoEvent;
import main.java.modelClasses.ModelEvents;
import main.java.modelClasses.ModelPersons;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//import jSonInput.*;



/**
 * Created by logan on 10/30/2017.
 */

//takes care of everything creation in the fill function
public class Creator{

    EncodeDecode coder = new EncodeDecode();

    int events_added;

    final String FEMALE_NAME_FILE_PATH = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/fnames.json";
    final String MALE_NAME_FILE_PATH = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/mnames.json";
    final String LAST_NAME_FILE_PATH = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/snames.json";
    final String LOCATIONS = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/locations.json";


    int base_year = 2017;
    int gen = 0;
    int birth_year_of_one_individual = 0;

    public void seteventstozero(){
        events_added = 0;
    }

    public int getevents(){
        return events_added;
    }

    public ArrayList<ModelPersons> createCouple(String descendant, Connection conn, int generation, ModelPersons child) throws SQLException { //passes in the name of the user
        gen = generation;
        ArrayList<ModelPersons> parents = new ArrayList<ModelPersons>(); //father first, mother second
        ModelPersons father = createPerson("m", descendant, conn, child);
        ModelPersons mother = createPerson("f", descendant, conn);
        father.setSpouseID(mother.getPersonID());
        mother.setSpouseID(father.getPersonID());
        createMarriage(father, mother, conn);

        parents.add(father);
        parents.add(mother);

        return parents;
        //add to parents back in other function
    }

    public ModelPersons createPerson(String gender, String descendant, Connection conn, ModelPersons child) throws SQLException { //passes in the gender of the person to create

        String personID = UUID.randomUUID().toString().replace("-", "");
        String personID2 = personID.substring(0,8); //personID generated
        String first_name;
        first_name = returnNameFromFile(MALE_NAME_FILE_PATH);
        ModelPersons response = new ModelPersons(
                personID2,
                descendant,
                first_name,
                child.getLastName(),
                gender,
                null, //add these in other class
                null,
                null); //add in other method
        //create birth event
        createBirth(response, descendant, conn);
        return response;
    }

    public ModelPersons createPerson(String gender, String descendant, Connection conn) throws SQLException { //passes in the gender of the person to create

        String personID = UUID.randomUUID().toString().replace("-", "");
        String personID2 = personID.substring(0,8); //personID generated
        String first_name = returnNameFromFile(FEMALE_NAME_FILE_PATH);
        String last_name = returnNameFromFile(LAST_NAME_FILE_PATH);
        ModelPersons response = new ModelPersons(
                personID2,
                descendant,
                first_name,
                last_name,
                gender,
                null, //add these in other class
                null,
                null); //add in other method
        //create birth event
        createBirth(response, descendant, conn);
        return response;
    }

    public void createBirth(ModelPersons person, String descendant, Connection conn) throws SQLException {
        //pull in random city, country, long, and latitude
        String eventID = UUID.randomUUID().toString().replace("-", "");
        String eventID2 = eventID.substring(0,8);
        String personID = person.getPersonID();
        String eventType = "birth";
        int around_year_of_birth = base_year - 25 - (gen*20);
        int rand_year_of_birth = (around_year_of_birth - 5) + (int)(Math.random() * (((around_year_of_birth + 5) - (around_year_of_birth - 5)) + 1));
        birth_year_of_one_individual = rand_year_of_birth;
        ModelEvents birth = coder.changetoModelEvents(returnObjectFromFile(LOCATIONS));
        birth.setEventID(eventID2);
        birth.setPersonID(personID);
        birth.setAssociatedUsername(descendant);
        birth.setEventType(eventType);
        birth.setYear(rand_year_of_birth);
        DaoEvent eventAccess = new DaoEvent();
//            System.out.println("adding event");
            eventAccess.insert(birth, conn);

        events_added++;
        if(rand_year_of_birth < base_year - 92){
            createDeath(person, descendant, conn, rand_year_of_birth);
        }
    }

    public void createDeath(ModelPersons person, String descendant, Connection conn, int birthyear) throws SQLException { //if they are dead
        String eventID = UUID.randomUUID().toString().replace("-", "");
        String eventID2 = eventID.substring(0,8);
        String personID = person.getPersonID();
        int around_year_of_death = birthyear + 75;
        int rand_year_of_death = (around_year_of_death - 10) + (int)(Math.random() * (((around_year_of_death + 10) - (around_year_of_death - 10)) + 1));
        ModelEvents death = coder.changetoModelEvents(returnObjectFromFile(LOCATIONS));
        death.setEventID(eventID2);
        death.setPersonID(personID);
        death.setAssociatedUsername(descendant);
        death.setEventType("death");
        death.setYear(rand_year_of_death);
        DaoEvent eventAccess = new DaoEvent();
            eventAccess.insert(death, conn);
        events_added++;
    }

    public void createMarriage(ModelPersons father, ModelPersons mother, Connection conn) throws SQLException {
        int year_of_marriage = birth_year_of_one_individual + 18;
        String eventID = UUID.randomUUID().toString().replace("-", "");
        String eventID2 = eventID.substring(0,8);
        String eventID3 = UUID.randomUUID().toString().replace("-", "");
        String eventID4 = eventID3.substring(0,8);

        JSONObject obj = returnObjectFromFile(LOCATIONS);
        ModelEvents marriage1 = coder.changetoModelEvents(obj);
        marriage1.setEventID(eventID2);
        marriage1.setPersonID(father.getPersonID());
        marriage1.setAssociatedUsername(father.getAssociatedUsername());
        marriage1.setEventType("marriage");
        marriage1.setYear(year_of_marriage);

        ModelEvents marriage2 = coder.changetoModelEvents(obj);
        marriage2.setEventID(eventID4);
        marriage2.setPersonID(mother.getPersonID());
        marriage2.setAssociatedUsername(mother.getAssociatedUsername());
        marriage2.setEventType("marriage");
        marriage2.setYear(year_of_marriage);

        DaoEvent eventAccess = new DaoEvent();
            eventAccess.insert(marriage1, conn);
            eventAccess.insert(marriage2, conn);
        events_added++;
        events_added++; //two for marriage
    }


    public JSONObject returnObjectFromFile(String filename){
        JSONObject object = new JSONObject();
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filename));
            object = (JSONObject) obj;
            JSONArray data = (JSONArray) object.get("data");
            int index = ThreadLocalRandom.current().nextInt(0, data.size());
            return (JSONObject) data.get(index);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return object;
    }

    public String returnNameFromFile(String filename){
        try {
            Gson gson = new Gson();
            Names data = gson.fromJson(new FileReader(filename), Names.class);
            int index = ThreadLocalRandom.current().nextInt(0, data.data.length);
            return data.data[index];
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return("");
    }

    private class Names{
        public String[] data;
    }
}
