package familymap.client.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import familymap.server.modelClasses.ModelEvents;
import familymap.server.modelClasses.ModelPersons;

public class DataCache {

    private static DataCache instance = null;

    private Map<String, ModelPersons> peopleMap;
    private Map<String, ModelEvents> eventMap;

    private Map<String, List<ModelEvents>> peopleEventMap;

    private Settings settings;
    private List<String> eventTypesForUser;
    private Map<String, Colors> eventTypesColors;

    private ModelPersons user;
    private Set<ModelPersons> paternalAncestors;
    private Set<ModelPersons> maternalAncestors;
    private Map<String, List<ModelPersons>> personChildrenMap;

    private List<String> eventTypesForFemaleAncestors;
    private List<String> eventTypesForMaleAncestors;
    private List<String> allEventTypes;
    private String authToken;
    private boolean loggedIn = false;
    private boolean showFemaleEvents = true;
    private boolean showMaleEvents = true;
    private DataCache(){
        peopleMap = new HashMap<>();
        eventMap = new HashMap<>();
        peopleEventMap = new HashMap<>();

        settings = new Settings();
        eventTypesForUser= new ArrayList<>();
        eventTypesColors = new HashMap<>();

        user = new ModelPersons();
        paternalAncestors = new HashSet<>();
        maternalAncestors = new HashSet<>();
        personChildrenMap = new HashMap<>();

        eventTypesForFemaleAncestors = new ArrayList<>();
        eventTypesForMaleAncestors = new ArrayList<>();
        authToken = new String();
        loggedIn = false;
    }
    public static DataCache getInstance(){
        if(instance == null){
            instance = new DataCache();
        }
        return instance;
    }

//    public Map<String, ModelPersons> getPeopleMap() { return peopleMap; }
//    public void setPeopleMap(Map<String, ModelPersons> peopleMap) { this.peopleMap = peopleMap; }
//
//    public Map<String, ModelEvents> getEventMap() { return eventMap; }
//    public void setEventMap(Map<String, ModelEvents> eventMap) { this.eventMap = eventMap; }
//
//
//    public Map<String, List<ModelEvents>> getPeopleEventMap() { return peopleEventMap; }
//    public void setPeopleEventMap(Map<String, List<ModelEvents>> peopleEventMap) { this.peopleEventMap = peopleEventMap; }
//
    public Settings getSettings() { return settings; }
    public void setSettings(Settings settings) { this.settings = settings; }
//
//    public List<String> getEventTypesForUser() { return eventTypesForUser; }
//    public void setEventTypesForUser(List<String> eventTypesForUser) { this.eventTypesForUser = eventTypesForUser; }
//
//    public Map<String, Colors> getEventTypesColors() { return eventTypesColors; }
//    public void setEventTypesColors(Map<String, Colors> eventTypesColors) { this.eventTypesColors = eventTypesColors; }
//
//    public ModelPersons getUser() { return user; }
//    public void setUser(ModelPersons user) { this.user = user; }
//
//    public Set<ModelPersons> getPaternalAncestors() { return paternalAncestors; }
//    public void setPaternalAncestors(Set<ModelPersons> paternalAncestors) { this.paternalAncestors = paternalAncestors; }
//
//    public Set<ModelPersons> getMaternalAncestors() { return maternalAncestors; }
//    public void setMaternalAncestors(Set<ModelPersons> maternalAncestors) { this.maternalAncestors = maternalAncestors; }
//
//    public Map<String, List<ModelPersons>> getPersonChildrenMap() { return personChildrenMap; }
//    public void setPersonChildrenMap(Map<String, List<ModelPersons>> personChildrenMap) { this.personChildrenMap = personChildrenMap; }





    public void setPeople(ArrayList<ModelPersons> input) {
        user = input.get(0);

        for (int i = 0; i < input.size(); i++){
            peopleMap.put(input.get(i).getPersonID(), input.get(i));
        }

        createPaternalAndMaternalSets();
    }

    public void createPaternalAndMaternalSets(){
        ModelPersons motherOfUser = peopleMap.get(user.getMotherID());
        ModelPersons fatherOfUser = peopleMap.get(user.getFatherID());


        addParentsToOneSetOrTheOther(motherOfUser,true);
        addParentsToOneSetOrTheOther(fatherOfUser,false);


        for (Map.Entry<String, ModelPersons> entry : peopleMap.entrySet()) {
            String possibleParentPersonID = new String(entry.getValue().getPersonID());
            List<ModelPersons> childrenOfPossible = new ArrayList<>();

            for(Map.Entry<String, ModelPersons> entryTwo : peopleMap.entrySet()){
                if (possibleParentPersonID.equals(entryTwo.getValue().getFatherID()) || possibleParentPersonID.equals(entryTwo.getValue().getMotherID())){
                    childrenOfPossible.add(entryTwo.getValue());
                }
            }
            personChildrenMap.put(possibleParentPersonID, childrenOfPossible);
        }
    }

    private void addParentsToOneSetOrTheOther(ModelPersons personReceived, Boolean isMaternalSide){
        if (isMaternalSide){
            maternalAncestors.add(personReceived);
        } else {
            paternalAncestors.add(personReceived);
        }

        if (personReceived.getFatherID() == null || personReceived.getFatherID().equals("")){ //baseCase
            return;
        } else {
            if (isMaternalSide){
                ModelPersons motherOfPersonReceived = peopleMap.get(personReceived.getMotherID());
                ModelPersons fatherOfPersonReceived = peopleMap.get(personReceived.getFatherID());
                addParentsToOneSetOrTheOther(motherOfPersonReceived, true); //true indicates this person belongs to maternal set
                addParentsToOneSetOrTheOther(fatherOfPersonReceived, true);
            } else {
                ModelPersons motherOfPersonReceived = peopleMap.get(personReceived.getMotherID());
                ModelPersons fatherOfPersonReceived = peopleMap.get(personReceived.getFatherID());
                addParentsToOneSetOrTheOther(motherOfPersonReceived,false);
                addParentsToOneSetOrTheOther(fatherOfPersonReceived,false);
            }
        }
    }

    public void setEvents(ArrayList<ModelEvents> input){

        for (int i = 0; i < input.size(); i++){

            eventMap.put(input.get(i).getEventID(), input.get(i));

            if (peopleEventMap.containsKey(input.get(i).getPersonID())){ //we know that the List<ModelEvents> associated with the person exists
                //lets get it
                List<ModelEvents> eventListFromMap = peopleEventMap.get(input.get(i).getPersonID());

                if (input.get(i).getYear() < eventListFromMap.get(0).getYear()){

                    eventListFromMap.add(0, input.get(i));
                } else if (input.get(i).getYear() > eventListFromMap.get(eventListFromMap.size()-1).getYear()){
                    eventListFromMap.add(input.get(i));
                } else {
                    for (int j = 0; j < eventListFromMap.size() - 1; ++j){
                        if ((input.get(i).getYear() > eventListFromMap.get(j).getYear()) && (!(input.get(i).getYear() > eventListFromMap.get(j+1).getYear()))){
                            eventListFromMap.add(j+1, input.get(i));
                        }
                    }
                }
                peopleEventMap.put(input.get(i).getPersonID(), eventListFromMap);
            }
            else { // The list<ModelEvents> doesn't exist so this event is the first one associated with the person
                List<ModelEvents> in = new ArrayList<>();
                in.add(input.get(i));
                peopleEventMap.put(input.get(i).getPersonID(), in);
            }
        } //By here we've made a map of personID to event type and events are sorted already! :D

        createEventLists();
    }

    public void createEventLists(){

        Boolean handledMaleCase = false;
        Boolean handledFemaleCase = false;
        Boolean handledUserCase = false;

        for (Map.Entry<String, List<ModelEvents>> entry: peopleEventMap.entrySet()){
            if (handledFemaleCase && handledMaleCase && handledUserCase) {
                break;
            } else if(entry.getKey().equals(user.getPersonID())){
                for (int i = 0; i < entry.getValue().size(); i++){ //initializes users event types
                    eventTypesForUser.add(entry.getValue().get(i).getEventType().toLowerCase());
                }
                handledUserCase = true;
            } else if (peopleMap.get(entry.getKey()).getGender().equals("f") && !handledFemaleCase) { //initiliziaes female event types
                for (int i = 0; i < entry.getValue().size(); i++){
                    eventTypesForFemaleAncestors.add(entry.getValue().get(i).getEventType().toLowerCase());
                }
                handledFemaleCase = true;
            } else if (peopleMap.get(entry.getKey()).getGender().equals("m") && !handledMaleCase){
                for (int i = 0; i < entry.getValue().size(); i++){  //initiliazes male event types
                    eventTypesForMaleAncestors.add(entry.getValue().get(i).getEventType().toLowerCase());
                }
                handledMaleCase = true;
            }

        }
    }

    public List<ModelPersons> findParentsByPersonID(ModelPersons potentialChild){
        List<ModelPersons> parents = new ArrayList<>();

        for (Map.Entry<String, List<ModelPersons>> entryTwo: personChildrenMap.entrySet()){
            String possibleParent = new String(entryTwo.getKey());
            List<ModelPersons> children = entryTwo.getValue();

            for (int i = 0; i < children.size(); i++){
                if (potentialChild.getPersonID().equals(children.get(i).getPersonID())){
                    parents.add(peopleMap.get(possibleParent));
                }
            }
        }
        return parents;
    }

    public ModelPersons findSpouseByPersonID(ModelPersons potentialSpouse){
        ModelPersons foundSpouse = new ModelPersons();

        for (Map.Entry<String, ModelPersons> entry: peopleMap.entrySet()){
            if (entry.getValue().getSpouseID().equals(potentialSpouse.getPersonID())){
                return entry.getValue();
            }
        }
        return foundSpouse;
    }


    public Map<String,ModelPersons> getPeopleMap(){
        return peopleMap;
    }

    public ModelPersons getPersonById(String id){
        return peopleMap.get(id);
    }

    public Map<String, List<ModelEvents>> getPeopleEventMap() {
        return peopleEventMap;
    }

    public Map<String, ModelEvents> getEventMap(){
        return eventMap;
    }

    public List<ModelEvents> getEventsOfPersonByPersonId(String id){
        return peopleEventMap.get(id);
    }

    public ModelEvents getEventById(String id){
        return eventMap.get(id);
    }

    public ModelPersons getUser(){
        return user;
    }

    public Set<ModelPersons> getMaternalAncestors(){
        return maternalAncestors;
    }

    public Set<ModelPersons> getPaternalAncestors(){
        return paternalAncestors;
    }

    public List<String> getEventTypesForUser() {
        return eventTypesForUser;
    }

    public List<String> getEventTypesForFemaleAncestors() {
        return eventTypesForFemaleAncestors;
    }

    public List<String> getEventTypesForMaleAncestors() {
        return eventTypesForMaleAncestors;
    }

    public Map<String, List<ModelPersons>> getChildrenMap(){
        return personChildrenMap;
    }

    public void setEventMap(Map<String, ModelEvents> eventMap) {
        this.eventMap = eventMap;
    }

    public void setPeopleMap(Map<String, ModelPersons> peopleMap) {
        this.peopleMap = peopleMap;
    }

    public void setPeopleEventMap(Map<String, List<ModelEvents>> peopleEventMap) {
        this.peopleEventMap = peopleEventMap;
    }

    public void setUser(ModelPersons user) {
        this.user = user;
    }

    public void setPaternalAncestors(Set<ModelPersons> paternalAncestors) {
        this.paternalAncestors = paternalAncestors;
    }

    public void setMaternalAncestors(Set<ModelPersons> maternalAncestors) {
        this.maternalAncestors = maternalAncestors;
    }

    public void setEventTypesForUser(List<String> eventTypesForUser) {
        this.eventTypesForUser = eventTypesForUser;
    }

    public void setEventTypesForFemaleAncestors(List<String> eventTypesForFemaleAncestors) {
        this.eventTypesForFemaleAncestors = eventTypesForFemaleAncestors;
    }

    public void setEventTypesForMaleAncestors(List<String> eventTypesForMaleAncestors) {
        this.eventTypesForMaleAncestors = eventTypesForMaleAncestors;
    }

    public void setChildrenMap(Map<String, List<ModelPersons>> childrenMap) {
        this.personChildrenMap = childrenMap;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public List<String> getAllEventTypes() {
        return allEventTypes;
    }

    public void setAllEventTypes(List<String> allEventTypes) {
        this.allEventTypes = allEventTypes;
    }

    public boolean isShowMaleEvents() {
        return showMaleEvents;
    }

    public void setShowMaleEvents(boolean showMaleEvents) {
        this.showMaleEvents = showMaleEvents;
    }

    public boolean isShowFemaleEvents() {
        return showFemaleEvents;
    }

    public void setShowFemaleEvents(boolean showFemaleEvents) {
        this.showFemaleEvents = showFemaleEvents;
    }


    public void clearClientForTesting() {
        eventMap.clear();
        eventMap = null;
        peopleMap.clear();
        peopleMap = null;
        peopleEventMap.clear();
        peopleEventMap = null;
        user = null;
        paternalAncestors.clear();
        paternalAncestors = null;
        maternalAncestors.clear();
        maternalAncestors = null;
        eventTypesForUser.clear();
        eventTypesForUser = null;
        eventTypesForFemaleAncestors.clear();
        eventTypesForFemaleAncestors = null;
        eventTypesForMaleAncestors.clear();
        eventTypesForMaleAncestors = null;
        personChildrenMap.clear();
        personChildrenMap = null;
        authToken = null;
        instance = null;
    }

}

