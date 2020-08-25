package familymap.client.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import familymap.server.modelClasses.ModelEvents;
import familymap.server.modelClasses.ModelPersons;

public class DataCache {

    private static DataCache instance = null;

    public static DataCache getInstance() {
        if (instance == null) {
            instance = new DataCache();
        }
        return instance;
    }

    private Map<String, ModelEvents> eventMap;
    private Map<String, ModelPersons> peopleMap;
    private Map<String, List<ModelEvents>> peopleEventMap;

    private ModelPersons user;
    private Settings userSettings;

    private Set<ModelPersons> fatherSide;
    private Set<ModelPersons> motherSide;
    private List<String> eventTypes;
    private List<String> eventTypesFemale;
    private List<String> eventTypesMale;
    private List<String> allEventTypes;
    private Map<String, List<ModelPersons>> childrenMap;


    private DataCache() {
        peopleMap = new HashMap<>();
        peopleEventMap = new HashMap<>();
        eventMap = new HashMap<>();
        user = new ModelPersons();
        userSettings = new Settings();
        fatherSide = new HashSet<>();
        motherSide = new HashSet<>();
        eventTypes = new ArrayList<>();
        eventTypesFemale = new ArrayList<>();
        eventTypesMale = new ArrayList<>();
        childrenMap = new HashMap<>();
    }


    public void setPeopleMap(ArrayList<ModelPersons> input) {
        user = input.get(0);

        for (int i = 0; i < input.size(); i++) {
            peopleMap.put(input.get(i).getPersonID(), input.get(i));
        }

        ModelPersons motherOfUser = peopleMap.get(user.getMotherID());
        ModelPersons fatherOfUser = peopleMap.get(user.getFatherID());


        addParents(motherOfUser, true);
        addParents(fatherOfUser, false);


        for (Map.Entry<String, ModelPersons> entry : peopleMap.entrySet()) {
            String possibleParentPersonID = new String(entry.getValue().getPersonID());
            List<ModelPersons> childrenOfPossible = new ArrayList<>();

            for (Map.Entry<String, ModelPersons> entryTwo : peopleMap.entrySet()) {
                if (possibleParentPersonID.equals(entryTwo.getValue().getFatherID()) || possibleParentPersonID.equals(entryTwo.getValue().getMotherID())) {
                    childrenOfPossible.add(entryTwo.getValue());
                }
            }
            childrenMap.put(possibleParentPersonID, childrenOfPossible);
        }
    }


    private void addParents(ModelPersons person, Boolean femaleSide) {
        if (femaleSide) {
            motherSide.add(person);
        } else {
            fatherSide.add(person);
        }

        if (!(person.getFatherID() == null || person.getFatherID().equals(""))) {
            ModelPersons motherOfPersonReceived = peopleMap.get(person.getMotherID());
            ModelPersons fatherOfPersonReceived = peopleMap.get(person.getFatherID());
            if (femaleSide) {
                addParents(motherOfPersonReceived, true);
                addParents(fatherOfPersonReceived, true);
            } else {
                addParents(motherOfPersonReceived, false);
                addParents(fatherOfPersonReceived, false);
            }
        }
    }

    public void setEvents(ArrayList<ModelEvents> input) {

        for (int i = 0; i < input.size(); i++) {

            eventMap.put(input.get(i).getEventID(), input.get(i));

            if (peopleEventMap.containsKey(input.get(i).getPersonID())) {
                List<ModelEvents> eventListFromMap = peopleEventMap.get(input.get(i).getPersonID());

                if (input.get(i).getYear() < eventListFromMap.get(0).getYear()) {

                    eventListFromMap.add(0, input.get(i));
                } else if (input.get(i).getYear() > eventListFromMap.get(eventListFromMap.size() - 1).getYear()) {
                    eventListFromMap.add(input.get(i));
                } else {
                    for (int j = 0; j < eventListFromMap.size() - 1; ++j) {
                        if ((input.get(i).getYear() > eventListFromMap.get(j).getYear()) && (!(input.get(i).getYear() > eventListFromMap.get(j + 1).getYear()))) {
                            eventListFromMap.add(j + 1, input.get(i));
                        }
                    }
                }
                peopleEventMap.put(input.get(i).getPersonID(), eventListFromMap);
            } else {
                List<ModelEvents> in = new ArrayList<>();
                in.add(input.get(i));
                peopleEventMap.put(input.get(i).getPersonID(), in);
            }
        }

        createEventLists();
    }

    public void createEventLists() {

        Boolean male = false;
        Boolean female = false;
        Boolean use = false;


        for (Map.Entry<String, List<ModelEvents>> entry : peopleEventMap.entrySet()) {
            if (female && male && use) {
                break;
            } else if (entry.getKey().equals(user.getPersonID())) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    eventTypes.add(entry.getValue().get(i).getEventType().toLowerCase());
                }
                use = true;
            } else if (peopleMap.get(entry.getKey()).getGender().equals("f") && !female) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    eventTypesFemale.add(entry.getValue().get(i).getEventType().toLowerCase());
                }
                female = true;
            } else if (peopleMap.get(entry.getKey()).getGender().equals("m") && !male) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    eventTypesMale.add(entry.getValue().get(i).getEventType().toLowerCase());
                }
                male = true;
            }

        }
    }

    public List<ModelPersons> findParentsByPersonID(ModelPersons potentialChild) {
        List<ModelPersons> parents = new ArrayList<>();

        for (Map.Entry<String, List<ModelPersons>> entryTwo : childrenMap.entrySet()) {
            String possibleParent = entryTwo.getKey();
            List<ModelPersons> children = entryTwo.getValue();

            for (int i = 0; i < children.size(); i++) {
                if (potentialChild.getPersonID().equals(children.get(i).getPersonID())) {
                    parents.add(peopleMap.get(possibleParent));
                }
            }
        }
        return parents;
    }


    public ModelPersons getPersonById(String id) {
        return peopleMap.get(id);
    }

    public Map<String, List<ModelEvents>> getPeopleEventMap() {
        return peopleEventMap;
    }

    public Map<String, ModelEvents> getEventMap() {
        return eventMap;
    }

    public List<ModelEvents> getEventsOfPersonByPersonId(String id) {
        return peopleEventMap.get(id);
    }

    public ModelEvents getEventById(String id) {
        return eventMap.get(id);
    }

    public ModelPersons getUser() {
        return user;
    }

    public List<String> getEventTypes() {
        return eventTypes;
    }

    public List<String> getEventTypesFemale() {
        return eventTypesFemale;
    }

    public List<String> getEventTypesMale() {
        return eventTypesMale;
    }

    public Map<String, List<ModelPersons>> getChildrenMap() {
        return childrenMap;
    }

    public List<String> getAllEventTypes() {
        return allEventTypes;
    }

    public void setAllEventTypes(List<String> allEventTypes) {
        this.allEventTypes = allEventTypes;
    }

    public ModelPersons findMotherByPersonID(ModelPersons person) {
        try {
            for (Map.Entry<String, ModelPersons> entry : peopleMap.entrySet()) {
                if (entry.getValue().getMotherID().equals(person.getPersonID())) {
                    return entry.getValue();
                }
            }
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public ModelPersons findFatherByPersonID(ModelPersons person) {
        try {
            for (Map.Entry<String, ModelPersons> entry : peopleMap.entrySet()) {
                if (entry.getValue().getFatherID().equals(person.getPersonID())) {
                    return entry.getValue();
                }
            }
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public ModelPersons findSpouseByPersonID(ModelPersons person) {
        try {
            for (Map.Entry<String, ModelPersons> entry : peopleMap.entrySet()) {
                if (entry.getValue().getSpouseID().equals(person.getPersonID())) {
                    return entry.getValue();
                }
            }
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public ModelPersons findChildByPersonID(ModelPersons person) {
        try {
            for (Map.Entry<String, ModelPersons> entry : peopleMap.entrySet()) {
                if (entry.getValue().getMotherID().equals(person.getPersonID())) {
                    return entry.getValue();
                }
            }
        } catch (NullPointerException ignored) {
        }
        try {
            for (Map.Entry<String, ModelPersons> entry : peopleMap.entrySet()) {
                if (entry.getValue().getFatherID().equals(person.getPersonID())) {
                    return entry.getValue();
                }
            }
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }


    public ArrayList<DisplayObj> getOrderEvents(ModelPersons person) {


        ArrayList<ModelEvents> returnEvents = new ArrayList<ModelEvents>();
        List<ModelEvents> eventsToDisplay = DataCache.getInstance().getEventsOfPersonByPersonId(person.getPersonID());
        for (int i = 0; i < eventsToDisplay.size(); i++) {
            if (person.getPersonID().equals(eventsToDisplay.get(i).getPersonID())) {
                returnEvents.add(eventsToDisplay.get(i));
            }
        }
        for (int i = 0; i < 60; i++) {
            for (int j = 0; j < returnEvents.size() - 1; j++) {
                if (returnEvents.get(j).getYear() > returnEvents.get(j + 1).getYear()) {
                    Collections.swap(returnEvents, j, j + 1);
                }
            }
        }


        ArrayList<DisplayObj> eventRows = new ArrayList<>();

        for (int i = 0; i < returnEvents.size(); i++) {
            DisplayObj temp = new DisplayObj(returnEvents.get(i).getDescription() + "\n" + person.getDescription(), "event", returnEvents.get(i).getEventID());
            eventRows.add(temp);
        }
        return eventRows;

    }

    public ArrayList<DisplayObj> getOrderPeople(ModelPersons person) {

        ArrayList<DisplayObj> peopleList = new ArrayList<>();


        List<ModelPersons> personsChildren = new ArrayList<>();
        List<ModelPersons> personsParents;
        ModelPersons spouse;


        Map<String, List<ModelPersons>> childrenMap = DataCache.getInstance().getChildrenMap();
        if (childrenMap.containsKey(person.getPersonID())) {
            personsChildren = childrenMap.get(person.getPersonID());
        }

        personsParents = DataCache.getInstance().findParentsByPersonID(person);


        if (personsParents.size() != 0) {
            for (int i = 0; i < personsParents.size(); i++) {
                if (personsParents.get(i).getGender().equals("f")) {
                    DisplayObj temp = new DisplayObj(personsParents.get(i).getDescription() + "\n" + "Mother", "female", personsParents.get(i).getPersonID());
                    peopleList.add(temp);
                } else {
                    DisplayObj temp = new DisplayObj(personsParents.get(i).getDescription() + "\n" + "Father", "male", personsParents.get(i).getPersonID());
                    peopleList.add(temp);
                }

            }
        }

        if (personsChildren.size() != 0) {
            for (int i = 0; i < personsChildren.size(); i++) {
                if (personsChildren.get(i).getGender().equals("f")) {
                    DisplayObj temp = new DisplayObj(personsChildren.get(i).getDescription() + "\n" + "Daughter", "female", personsChildren.get(i).getPersonID());
                    peopleList.add(temp);
                } else {
                    DisplayObj temp = new DisplayObj(personsChildren.get(i).getDescription() + "\n" + "Son", "male", personsChildren.get(i).getPersonID());
                    peopleList.add(temp);
                }
            }
        }
        spouse = DataCache.getInstance().findSpouseByPersonID(person);
        if (spouse != null) {
            if (spouse.getPersonID() != null && !spouse.getPersonID().equals("")) {
                if (spouse.getGender().equals("f")) {
                    DisplayObj temp = new DisplayObj(spouse.getDescription() + "\n" + "Wife", "female", spouse.getPersonID());
                    peopleList.add(temp);
                } else {
                    DisplayObj temp = new DisplayObj(spouse.getDescription() + "\n" + "Husband", "male", spouse.getPersonID());
                    peopleList.add(temp);
                }
            }
        }
        return peopleList;
    }

    public Settings getUserSettings() {
        return userSettings;
    }


    public ModelPersons getPersonByEvent(ModelEvents event) {
        try {
            for (Map.Entry<String, ModelPersons> entry : peopleMap.entrySet()) {
                if (entry.getValue().getPersonID().equals(event.getPersonID())) {
                    return entry.getValue();
                }
            }
            return null;
        } catch (NullPointerException e) {
            return null;
        }

    }

    public ModelPersons findPersonByID(String ID) {
        try {
            for (Map.Entry<String, ModelPersons> entry : peopleMap.entrySet()) {
                if (entry.getValue().getPersonID().equals(ID)) {
                    return entry.getValue();
                }
            }
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public ModelEvents findFirstEvent(ModelPersons person) {
        int earliestYear = Integer.MAX_VALUE;
        ModelEvents earliestEvent = null;
        try {
            for (Map.Entry<String, ModelEvents> entry : eventMap.entrySet()) {
                if (entry.getValue().getPersonID().equals(person.getPersonID())) {
                    if (earliestEvent == null || entry.getValue().getYear() < earliestYear) {
                        earliestYear = entry.getValue().getYear();
                        earliestEvent = entry.getValue();
                    }
                }
            }
            return earliestEvent;
        } catch (NullPointerException e) {
            return null;
        }

    }

    public ArrayList<ModelEvents> getOrderLifeEvents(ModelPersons person) {

        ArrayList<ModelEvents> returnEvents = new ArrayList<>();
        List<ModelEvents> eventsToDisplay = DataCache.getInstance().getEventsOfPersonByPersonId(person.getPersonID());
        for (int i = 0; i < eventsToDisplay.size(); i++) {
            if (person.getPersonID().equals(eventsToDisplay.get(i).getPersonID())) {
                returnEvents.add(eventsToDisplay.get(i));
            }
        }
        for (int i = 0; i < 60; i++) {
            for (int j = 0; j < returnEvents.size() - 1; j++) {
                if (returnEvents.get(j).getYear() > returnEvents.get(j + 1).getYear()) {
                    Collections.swap(returnEvents, j, j + 1);
                }
            }
        }
        return returnEvents;
    }

    ArrayList<DisplayObj> searchList = new ArrayList<>();

    public void search(String searchString) {
        searchList.clear();

        for (Map.Entry<String, ModelPersons> entry : peopleMap.entrySet()) {
            if (entry.getValue().getLastName().toLowerCase().contains(searchString.toLowerCase())) {
                DisplayObj obj = new DisplayObj(entry.getValue());
                searchList.add(obj);
            } else if (entry.getValue().getFirstName().toLowerCase().contains(searchString.toLowerCase())) {
                DisplayObj obj = new DisplayObj(entry.getValue());
                searchList.add(obj);
            }
        }
        for (Map.Entry<String, ModelEvents> entry : eventMap.entrySet()) {
            String compareYear = Integer.toString(entry.getValue().getYear());
            if (compareYear.contains(searchString)) {
                DisplayObj obj = new DisplayObj(entry.getValue());
                searchList.add(obj);
            } else if (entry.getValue().getEventType().toLowerCase().contains(searchString.toLowerCase())) {
                DisplayObj obj = new DisplayObj(entry.getValue());
                searchList.add(obj);
            } else if (entry.getValue().getCountry().toLowerCase().contains(searchString.toLowerCase())) {
                DisplayObj obj = new DisplayObj(entry.getValue());
                searchList.add(obj);
            } else if (entry.getValue().getCity().toLowerCase().contains(searchString.toLowerCase())) {
                DisplayObj obj = new DisplayObj(entry.getValue());
                searchList.add(obj);
            }
        }
    }

    public ArrayList<DisplayObj> getSearchList() {
        return searchList;
    }

    public String getEventInfo(ModelEvents event) {
        String toReturn = event.getEventType().toLowerCase() + ": " + event.getCity() + ", "
                + event.getCountry() + " (" + event.getYear() + ")";
        return toReturn;
    }

    public Map<String, ModelEvents> getEventMapMaternal() {
        Map<String, ModelEvents> temp = new HashMap<>();
        List<String> tempID = new ArrayList<>();

        for (ModelPersons t : motherSide) {
            tempID.add(t.getPersonID());
        }
        for (Map.Entry<String, ModelEvents> entry : eventMap.entrySet()) {
            if (tempID.contains(entry.getValue().getPersonID())) {
                temp.put(entry.getValue().getEventID(), entry.getValue());
            }
        }
        return temp;

    }

    public Map<String, ModelEvents> getEventMapPaternal() {
        Map<String, ModelEvents> temp = new HashMap<>();
        List<String> tempID = new ArrayList<>();

        for (ModelPersons t : fatherSide) {
            tempID.add(t.getPersonID());
        }
        for (Map.Entry<String, ModelEvents> entry : eventMap.entrySet()) {
            if (tempID.contains(entry.getValue().getPersonID())) {
                temp.put(entry.getValue().getEventID(), entry.getValue());
            }
        }
        return temp;

    }

    public Map<String, ModelPersons> getPeopleMap() {
        return peopleMap;
    }

    public void setPeople(ArrayList<ModelPersons> family) {
        for (ModelPersons t : family) {
            peopleMap.put(t.getPersonID(), t);
        }
    }


    public void clearDataCache() {
        eventMap.clear();
        eventMap = null;
        peopleMap.clear();
        peopleMap = null;
        peopleEventMap.clear();
        peopleEventMap = null;
        user = null;
        fatherSide.clear();
        fatherSide = null;
        motherSide.clear();
        motherSide = null;
        eventTypes.clear();
        eventTypes = null;
        eventTypesFemale.clear();
        eventTypesFemale = null;
        eventTypesMale.clear();
        eventTypesMale = null;
        childrenMap.clear();
        childrenMap = null;
        instance = null;
    }
}


