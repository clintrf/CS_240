package familymap.client;



import androidx.collection.ArraySet;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;

import familymap.client.Model.DataCache;
import familymap.server.modelClasses.ModelEvents;
import familymap.server.modelClasses.ModelPersons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class DataCacheTesting {
    private Map<String, ModelEvents> eventMap;
    private Map<String, ModelPersons> peopleMap;
    private Map<String, List<ModelEvents>> peopleEventMap;
    private ModelPersons user;
    private Set<ModelPersons> paternalAncestors;
    private Set<ModelPersons> maternalAncestors;
    private List<String> eventTypesForUser;
    private List<String> eventTypesForFemaleAncestors;
    private List<String> eventTypesForMaleAncestors;
    private Map<String, List<ModelPersons>> childrenMap;


    @Before
    public void setUp(){
        eventMap = DataCache.getInstance().getEventMap();
        peopleMap = DataCache.getInstance().getPeopleMap();
        peopleEventMap = DataCache.getInstance().getPeopleEventMap();
        user = DataCache.getInstance().getUser();
        eventTypesForUser = DataCache.getInstance().getEventTypes();
        eventTypesForFemaleAncestors = DataCache.getInstance().getEventTypesFemale();
        eventTypesForMaleAncestors = DataCache.getInstance().getEventTypesMale();
        childrenMap = DataCache.getInstance().getChildrenMap();
        DataCache.getInstance().clearDataCache();
    }


    @Test
    public void testSetPeople(){
        ModelPersons person1 = new ModelPersons();
        ModelPersons person2 = new ModelPersons();
        ModelPersons person3 = new ModelPersons();
        ModelPersons person4 = new ModelPersons();
        ModelPersons person5 = new ModelPersons();
        ModelPersons person6 = new ModelPersons();
        ModelPersons person7 = new ModelPersons();

        person1.setAssociatedUsername("username1");
        person2.setAssociatedUsername("username2");
        person3.setAssociatedUsername("username3");
        person4.setAssociatedUsername("username4");
        person5.setAssociatedUsername("username5");
        person6.setAssociatedUsername("username6");
        person7.setAssociatedUsername("username7");

        person1.setGender("m");
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstName("firstName");
        person1.setLastName("lastName");
        person1.setPersonID("personID1");
        person1.setFatherID("personID1 father");
        person1.setMotherID("personID1 mother");

        person2.setFirstName("firstName father");
        person2.setLastName("lastName");
        person2.setPersonID("personID2 father");
        person2.setFatherID("personID2 paternal grandpa");
        person2.setMotherID("personID2 paternal grandma");

        person3.setFirstName("firstName mother");
        person3.setLastName("lastName");
        person3.setPersonID("personID3 mother");
        person3.setFatherID("personID3 maternal grandpa");
        person3.setMotherID("personID3 maternal grandma");

        person4.setFirstName("firstName paternal grandpa");
        person4.setLastName("lastName");
        person4.setPersonID("personID4 paternal grandpa");

        person5.setFirstName("firstName paternal grandma");
        person5.setLastName("lastName");
        person5.setPersonID("personID5 paternal grandma");

        person6.setFirstName("firstName maternal grandpa");
        person6.setLastName("lastName");
        person6.setPersonID("personID6 maternal grandpa");

        person7.setFirstName("firstName maternal grandma");
        person7.setLastName("lastName");
        person7.setPersonID("personID7 maternal grandma");

        ArrayList<ModelPersons> family = new ArrayList<>();
        family.add( person1);
        family.add( person2);
        family.add( person3);
        family.add( person4);
        family.add( person5);
        family.add( person6);
        family.add( person7);


        Map<String, ModelPersons> temp = DataCache.getInstance().getPeopleMap();

        for(Map.Entry<String, ModelPersons> entry : peopleMap.entrySet()){
            assertTrue(temp.containsValue( entry.getValue()));
        }
    }


    @Test
    public void testFindParentsByID(){
        ModelPersons person1 = new ModelPersons();
        ModelPersons person2 = new ModelPersons();
        ModelPersons person3 = new ModelPersons();
        ModelPersons person4 = new ModelPersons();
        ModelPersons person5 = new ModelPersons();
        ModelPersons person6 = new ModelPersons();
        ModelPersons person7 = new ModelPersons();

        person1.setAssociatedUsername("username1");
        person2.setAssociatedUsername("username2");
        person3.setAssociatedUsername("username3");
        person4.setAssociatedUsername("username4");
        person5.setAssociatedUsername("username5");
        person6.setAssociatedUsername("username6");
        person7.setAssociatedUsername("username7");

        person1.setGender("m");
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstName("firstName");
        person1.setLastName("lastName");
        person1.setPersonID("personID1");
        person1.setFatherID("personID1 father");
        person1.setMotherID("personID1 mother");

        person2.setFirstName("firstName father");
        person2.setLastName("lastName");
        person2.setPersonID("personID1 father");
        person2.setFatherID("personID1 paternal grandpa");
        person2.setMotherID("personID1 paternal grandma");

        person3.setFirstName("firstName mother");
        person3.setLastName("lastName");
        person3.setPersonID("personID1 mother");
        person3.setFatherID("personID1 maternal grandpa");
        person3.setMotherID("personID1 maternal grandma");

        person4.setFirstName("firstName paternal grandpa");
        person4.setLastName("lastName");
        person4.setPersonID("personID1 paternal grandpa");

        person5.setFirstName("firstName paternal grandma");
        person5.setLastName("lastName");
        person5.setPersonID("personID1 paternal grandma");

        person6.setFirstName("firstName maternal grandpa");
        person6.setLastName("lastName");
        person6.setPersonID("personID1 maternal grandpa");

        person7.setFirstName("firstName maternal grandma");
        person7.setLastName("lastName");
        person7.setPersonID("personID1 maternal grandma");

        ArrayList<ModelPersons> family = new ArrayList<>();
        family.add( person1);
        family.add( person2);
        family.add( person3);
        family.add( person4);
        family.add( person5);
        family.add( person6);
        family.add( person7);



        DataCache.getInstance().setPeopleMap(family);

        List<ModelPersons> expectedParents = new ArrayList<>();
        expectedParents.add(person2);
        expectedParents.add(person3);

        List<ModelPersons> output = DataCache.getInstance().findParentsByPersonID(person1);
        assertTrue(expectedParents.contains(output.get(0)));


    }


    @Test
    public void testFindSpouseByPersonID(){
        ModelPersons person1 = new ModelPersons();
        ModelPersons person2 = new ModelPersons();
        ModelPersons person3 = new ModelPersons();
        ModelPersons person4 = new ModelPersons();
        ModelPersons person5 = new ModelPersons();
        ModelPersons person6 = new ModelPersons();
        ModelPersons person7 = new ModelPersons();

        person1.setAssociatedUsername("username1");
        person2.setAssociatedUsername("username2");
        person3.setAssociatedUsername("username3");
        person4.setAssociatedUsername("username4");
        person5.setAssociatedUsername("username5");
        person6.setAssociatedUsername("username6");
        person7.setAssociatedUsername("username7");

        person1.setGender("m");
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstName("firstName");
        person1.setLastName("lastName");
        person1.setPersonID("personID1");
        person1.setFatherID("personID1 father");
        person1.setMotherID("personID1 mother");

        person2.setFirstName("firstName father");
        person2.setLastName("lastName");
        person2.setPersonID("personID1 father");
        person2.setFatherID("personID1 paternal grandpa");
        person2.setMotherID("personID1 paternal grandma");

        person3.setFirstName("firstName mother");
        person3.setLastName("lastName");
        person3.setPersonID("personID1 mother");
        person3.setFatherID("personID1 maternal grandpa");
        person3.setMotherID("personID1 maternal grandma");

        person4.setFirstName("firstName paternal grandpa");
        person4.setLastName("lastName");
        person4.setPersonID("personID1 paternal grandpa");

        person5.setFirstName("firstName paternal grandma");
        person5.setLastName("lastName");
        person5.setPersonID("personID1 paternal grandma");

        person6.setFirstName("firstName maternal grandpa");
        person6.setLastName("lastName");
        person6.setPersonID("personID1 maternal grandpa");

        person7.setFirstName("firstName maternal grandma");
        person7.setLastName("lastName");
        person7.setPersonID("personID1 maternal grandma");
        person2.setSpouseID(person3.getPersonID());
        person3.setSpouseID(person2.getPersonID());

        person4.setSpouseID(person5.getPersonID());
        person5.setSpouseID(person4.getPersonID());

        person6.setSpouseID(person7.getPersonID());
        person7.setSpouseID(person6.getPersonID());

        ArrayList<ModelPersons> family = new ArrayList<>();
        family.add( person1);
        family.add( person2);
        family.add( person3);
        family.add( person4);
        family.add( person5);
        family.add( person6);
        family.add( person7);


        DataCache.getInstance().setPeopleMap(family);

        ModelPersons output = DataCache.getInstance().findSpouseByPersonID(person3);

        assertEquals(person2, output);

        output = DataCache.getInstance().findSpouseByPersonID(person6);

        assertEquals(person7, output);

    }

    @Test
    public void testCreatePaternalAndMaternalSets(){
        ModelPersons person1 = new ModelPersons();
        ModelPersons person2 = new ModelPersons();
        ModelPersons person3 = new ModelPersons();
        ModelPersons person4 = new ModelPersons();
        ModelPersons person5 = new ModelPersons();
        ModelPersons person6 = new ModelPersons();
        ModelPersons person7 = new ModelPersons();

        person1.setAssociatedUsername("username1");
        person2.setAssociatedUsername("username2");
        person3.setAssociatedUsername("username3");
        person4.setAssociatedUsername("username4");
        person5.setAssociatedUsername("username5");
        person6.setAssociatedUsername("username6");
        person7.setAssociatedUsername("username7");

        person1.setGender("m");
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstName("firstName");
        person1.setLastName("lastName");
        person1.setPersonID("personID1");
        person1.setFatherID("personID1 father");
        person1.setMotherID("personID1 mother");

        person2.setFirstName("firstName father");
        person2.setLastName("lastName");
        person2.setPersonID("personID1 father");
        person2.setFatherID("personID1 paternal grandpa");
        person2.setMotherID("personID1 paternal grandma");

        person3.setFirstName("firstName mother");
        person3.setLastName("lastName");
        person3.setPersonID("personID1 mother");
        person3.setFatherID("personID1 maternal grandpa");
        person3.setMotherID("personID1 maternal grandma");

        person4.setFirstName("firstName paternal grandpa");
        person4.setLastName("lastName");
        person4.setPersonID("personID1 paternal grandpa");

        person5.setFirstName("firstName paternal grandma");
        person5.setLastName("lastName");
        person5.setPersonID("personID1 paternal grandma");

        person6.setFirstName("firstName maternal grandpa");
        person6.setLastName("lastName");
        person6.setPersonID("personID1 maternal grandpa");

        person7.setFirstName("firstName maternal grandma");
        person7.setLastName("lastName");
        person7.setPersonID("personID1 maternal grandma");
        person2.setSpouseID(person3.getPersonID());
        person3.setSpouseID(person2.getPersonID());

        person4.setSpouseID(person5.getPersonID());
        person5.setSpouseID(person4.getPersonID());

        person6.setSpouseID(person7.getPersonID());
        person7.setSpouseID(person6.getPersonID());

        ArrayList<ModelPersons> family = new ArrayList<>();
        family.add( person1);
        family.add( person2);
        family.add( person3);
        family.add( person4);
        family.add( person5);
        family.add( person6);
        family.add( person7);


        DataCache.getInstance().setPeopleMap(family);

        ArrayList< ModelPersons> expectedParentalAncestors = new ArrayList<>();
        expectedParentalAncestors.add(person4);
        expectedParentalAncestors.add(person2);
        expectedParentalAncestors.add(person5);

        ArrayList<ModelPersons> expectedMaternalAncestors = new ArrayList<>();
        expectedMaternalAncestors.add(person7);
        expectedMaternalAncestors.add(person6);
        expectedMaternalAncestors.add(person3);


        assertEquals((DataCache.getInstance().getPeopleMapPaternal().values()).toString(), expectedParentalAncestors.toString());
        assertEquals((DataCache.getInstance().getPeopleMapMaternal().values()).toString(), expectedMaternalAncestors.toString());
    }

    @Test
    public void testSetEvents(){
        ModelPersons person1 = new ModelPersons();
        ModelPersons person2 = new ModelPersons();
        ModelPersons person3 = new ModelPersons();
        ModelPersons person4 = new ModelPersons();
        ModelPersons person5 = new ModelPersons();
        ModelPersons person6 = new ModelPersons();
        ModelPersons person7 = new ModelPersons();

        person1.setAssociatedUsername("username1");
        person2.setAssociatedUsername("username2");
        person3.setAssociatedUsername("username3");
        person4.setAssociatedUsername("username4");
        person5.setAssociatedUsername("username5");
        person6.setAssociatedUsername("username6");
        person7.setAssociatedUsername("username7");

        person1.setGender("m");
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstName("firstName");
        person1.setLastName("lastName");
        person1.setPersonID("personID1");
        person1.setFatherID("personID1 father");
        person1.setMotherID("personID1 mother");

        person2.setFirstName("firstName father");
        person2.setLastName("lastName");
        person2.setPersonID("personID1 father");
        person2.setFatherID("personID1 paternal grandpa");
        person2.setMotherID("personID1 paternal grandma");

        person3.setFirstName("firstName mother");
        person3.setLastName("lastName");
        person3.setPersonID("personID1 mother");
        person3.setFatherID("personID1 maternal grandpa");
        person3.setMotherID("personID1 maternal grandma");

        person4.setFirstName("firstName paternal grandpa");
        person4.setLastName("lastName");
        person4.setPersonID("personID1 paternal grandpa");

        person5.setFirstName("firstName paternal grandma");
        person5.setLastName("lastName");
        person5.setPersonID("personID1 paternal grandma");

        person6.setFirstName("firstName maternal grandpa");
        person6.setLastName("lastName");
        person6.setPersonID("personID1 maternal grandpa");

        person7.setFirstName("firstName maternal grandma");
        person7.setLastName("lastName");
        person7.setPersonID("personID1 maternal grandma");
        person2.setSpouseID(person3.getPersonID());
        person3.setSpouseID(person2.getPersonID());

        person4.setSpouseID(person5.getPersonID());
        person5.setSpouseID(person4.getPersonID());

        person6.setSpouseID(person7.getPersonID());
        person7.setSpouseID(person6.getPersonID());

        ArrayList<ModelPersons> family = new ArrayList<>();
        family.add( person1);
        family.add( person2);
        family.add( person3);
        family.add( person4);
        family.add( person5);
        family.add( person6);
        family.add( person7);


        DataCache.getInstance().setPeopleMap(family);


        ModelEvents event1 = new ModelEvents(
                "eventID1",
                "username1",
                "personID1",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                2000);
        ModelEvents event2 = new ModelEvents(
                "eventID2",
                "username1",
                "personID1",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                2000);
        ModelEvents event3 = new ModelEvents(
                "eventID3",
                "username1",
                "personID1",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                2000);
        ModelEvents event4 = new ModelEvents(
                "eventID4",
                "username1",
                "personID1",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                2000);
        ModelEvents event5 = new ModelEvents(
                "eventID5",
                "username1",
                "personID1",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                2000);
        ModelEvents event6 = new ModelEvents(
                "eventID6",
                "username1",
                "personID1",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                2000);

        ArrayList<ModelEvents> familyEvents = new ArrayList<>();

        familyEvents.add(event1);
        familyEvents.add(event2);
        familyEvents.add(event3);
        familyEvents.add(event4);
        familyEvents.add(event5);
        familyEvents.add(event6);


        DataCache.getInstance().setEvents(familyEvents);

        Map<String, ModelEvents> events = DataCache.getInstance().getEventMap();

        for (int i = 0; i < familyEvents.size(); i++){
            assertEquals(familyEvents.get(i), events.get(familyEvents.get(i).getEventID()));
        }
    }

    @Test
    public void testCreateEventsLists(){
        ModelPersons person1 = new ModelPersons();
        ModelPersons person2 = new ModelPersons();
        ModelPersons person3 = new ModelPersons();
        ModelPersons person4 = new ModelPersons();
        ModelPersons person5 = new ModelPersons();
        ModelPersons person6 = new ModelPersons();
        ModelPersons person7 = new ModelPersons();

        person1.setAssociatedUsername("username1");
        person2.setAssociatedUsername("username2");
        person3.setAssociatedUsername("username3");
        person4.setAssociatedUsername("username4");
        person5.setAssociatedUsername("username5");
        person6.setAssociatedUsername("username6");
        person7.setAssociatedUsername("username7");

        person1.setGender("m");
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstName("firstName");
        person1.setLastName("lastName");
        person1.setPersonID("personID1");
        person1.setFatherID("personID1 father");
        person1.setMotherID("personID1 mother");

        person2.setFirstName("firstName father");
        person2.setLastName("lastName");
        person2.setPersonID("personID1 father");
        person2.setFatherID("personID1 paternal grandpa");
        person2.setMotherID("personID1 paternal grandma");

        person3.setFirstName("firstName mother");
        person3.setLastName("lastName");
        person3.setPersonID("personID1 mother");
        person3.setFatherID("personID1 maternal grandpa");
        person3.setMotherID("personID1 maternal grandma");

        person4.setFirstName("firstName paternal grandpa");
        person4.setLastName("lastName");
        person4.setPersonID("personID1 paternal grandpa");

        person5.setFirstName("firstName paternal grandma");
        person5.setLastName("lastName");
        person5.setPersonID("personID1 paternal grandma");

        person6.setFirstName("firstName maternal grandpa");
        person6.setLastName("lastName");
        person6.setPersonID("personID1 maternal grandpa");

        person7.setFirstName("firstName maternal grandma");
        person7.setLastName("lastName");
        person7.setPersonID("personID1 maternal grandma");
        person2.setSpouseID(person3.getPersonID());
        person3.setSpouseID(person2.getPersonID());

        person4.setSpouseID(person5.getPersonID());
        person5.setSpouseID(person4.getPersonID());

        person6.setSpouseID(person7.getPersonID());
        person7.setSpouseID(person6.getPersonID());

        ArrayList<ModelPersons> family = new ArrayList<>();
        family.add( person1);
        family.add( person2);
        family.add( person3);
        family.add( person4);
        family.add( person5);
        family.add( person6);
        family.add( person7);


        DataCache.getInstance().setPeopleMap(family);


        ModelEvents event1 = new ModelEvents(
                "eventID1",
                "username1",
                "personID1",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                2000);
        ModelEvents event2 = new ModelEvents(
                "eventID2",
                "username1",
                "personID1",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                2001);
        ModelEvents event3 = new ModelEvents(
                "eventID3",
                "username1",
                "personID1",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                2002);
        ModelEvents event4 = new ModelEvents(
                "eventID4",
                "username1",
                "personID1 father",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                2003);
        ModelEvents event5 = new ModelEvents(
                "eventID5",
                "username1",
                "personID1 father",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                2004);
        ModelEvents event6 = new ModelEvents(
                "eventID6",
                "username1",
                "personID1 mother",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                200);

        ArrayList<ModelEvents> familyEvents = new ArrayList<>();

        familyEvents.add(event1);
        familyEvents.add(event2);
        familyEvents.add(event3);
        familyEvents.add(event4);
        familyEvents.add(event5);
        familyEvents.add(event6);


        DataCache.getInstance().setEvents(familyEvents);

        List<ModelEvents> expectedEventsOfUser = new ArrayList<>();
        expectedEventsOfUser.add(event1);
        expectedEventsOfUser.add(event2);
        expectedEventsOfUser.add(event3);

        assertEquals(expectedEventsOfUser,  DataCache.getInstance().getEventsOfPersonByPersonId("personID1"));

    }


    @Test
    public void testFindParentsByIDShouldFail(){
        ModelPersons person1 = new ModelPersons();
        ModelPersons person2 = new ModelPersons();
        ModelPersons person3 = new ModelPersons();
        ModelPersons person4 = new ModelPersons();
        ModelPersons person5 = new ModelPersons();
        ModelPersons person6 = new ModelPersons();
        ModelPersons person7 = new ModelPersons();

        person1.setAssociatedUsername("username1");
        person2.setAssociatedUsername("username2");
        person3.setAssociatedUsername("username3");
        person4.setAssociatedUsername("username4");
        person5.setAssociatedUsername("username5");
        person6.setAssociatedUsername("username6");
        person7.setAssociatedUsername("username7");

        person1.setGender("m");
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstName("firstName");
        person1.setLastName("lastName");
        person1.setPersonID("personID1");
        person1.setFatherID("personID1 father");
        person1.setMotherID("personID1 mother");

        person2.setFirstName("firstName father");
        person2.setLastName("lastName");
        person2.setPersonID("personID1 father");
        person2.setFatherID("personID1 paternal grandpa");
        person2.setMotherID("personID1 paternal grandma");

        person3.setFirstName("firstName mother");
        person3.setLastName("lastName");
        person3.setPersonID("personID1 mother");
        person3.setFatherID("personID1 maternal grandpa");
        person3.setMotherID("personID1 maternal grandma");

        person4.setFirstName("firstName paternal grandpa");
        person4.setLastName("lastName");
        person4.setPersonID("personID1 paternal grandpa");

        person5.setFirstName("firstName paternal grandma");
        person5.setLastName("lastName");
        person5.setPersonID("personID1 paternal grandma");

        person6.setFirstName("firstName maternal grandpa");
        person6.setLastName("lastName");
        person6.setPersonID("personID1 maternal grandpa");

        person7.setFirstName("firstName maternal grandma");
        person7.setLastName("lastName");
        person7.setPersonID("personID1 maternal grandma");
        person2.setSpouseID(person3.getPersonID());
        person3.setSpouseID(person2.getPersonID());

        person4.setSpouseID(person5.getPersonID());
        person5.setSpouseID(person4.getPersonID());

        person6.setSpouseID(person7.getPersonID());
        person7.setSpouseID(person6.getPersonID());

        ArrayList<ModelPersons> family = new ArrayList<>();
        family.add( person1);
        family.add( person2);
        family.add( person3);
        family.add( person4);
        family.add( person5);
        family.add( person6);
        family.add( person7);


        DataCache.getInstance().setPeopleMap(family);


        ModelEvents event1 = new ModelEvents(
                "eventID1",
                "username1",
                "personID1",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                2000);
        ModelEvents event2 = new ModelEvents(
                "eventID2",
                "username1",
                "personID1",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                2001);
        ModelEvents event3 = new ModelEvents(
                "eventID3",
                "username1",
                "personID1",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                2002);
        ModelEvents event4 = new ModelEvents(
                "eventID4",
                "username1",
                "personID1 father",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                2003);
        ModelEvents event5 = new ModelEvents(
                "eventID5",
                "username1",
                "personID1 father",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                2004);
        ModelEvents event6 = new ModelEvents(
                "eventID6",
                "username1",
                "personID1 mother",
                1.1,
                2.2,
                "USA",
                "oronoco",
                "ev",
                200);

        ArrayList<ModelEvents> familyEvents = new ArrayList<>();

        familyEvents.add(event1);
        familyEvents.add(event2);
        familyEvents.add(event3);
        familyEvents.add(event4);
        familyEvents.add(event5);
        familyEvents.add(event6);


        DataCache.getInstance().setEvents(familyEvents);

        List<ModelPersons> expectedParents = new ArrayList<>();
        expectedParents.add(person4);
        expectedParents.add(person5);

        List<ModelPersons> output = DataCache.getInstance().findParentsByPersonID(person1);
        assertFalse(expectedParents.contains(output.get(0)));
        assertFalse(expectedParents.contains(output.get(1)));
    }


    @Test
    public void testFindSpouseByPersonIDShouldFail(){
        ModelPersons person1 = new ModelPersons();
        ModelPersons person2 = new ModelPersons();
        ModelPersons person3 = new ModelPersons();
        ModelPersons person4 = new ModelPersons();
        ModelPersons person5 = new ModelPersons();
        ModelPersons person6 = new ModelPersons();
        ModelPersons person7 = new ModelPersons();

        person1.setAssociatedUsername("username1");
        person2.setAssociatedUsername("username2");
        person3.setAssociatedUsername("username3");
        person4.setAssociatedUsername("username4");
        person5.setAssociatedUsername("username5");
        person6.setAssociatedUsername("username6");
        person7.setAssociatedUsername("username7");

        person1.setGender("m");
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstName("firstName");
        person1.setLastName("lastName");
        person1.setPersonID("personID1");
        person1.setFatherID("personID1 father");
        person1.setMotherID("personID1 mother");

        person2.setFirstName("firstName father");
        person2.setLastName("lastName");
        person2.setPersonID("personID1 father");
        person2.setFatherID("personID1 paternal grandpa");
        person2.setMotherID("personID1 paternal grandma");

        person3.setFirstName("firstName mother");
        person3.setLastName("lastName");
        person3.setPersonID("personID1 mother");
        person3.setFatherID("personID1 maternal grandpa");
        person3.setMotherID("personID1 maternal grandma");

        person4.setFirstName("firstName paternal grandpa");
        person4.setLastName("lastName");
        person4.setPersonID("personID1 paternal grandpa");

        person5.setFirstName("firstName paternal grandma");
        person5.setLastName("lastName");
        person5.setPersonID("personID1 paternal grandma");

        person6.setFirstName("firstName maternal grandpa");
        person6.setLastName("lastName");
        person6.setPersonID("personID1 maternal grandpa");

        person7.setFirstName("firstName maternal grandma");
        person7.setLastName("lastName");
        person7.setPersonID("personID1 maternal grandma");
        person2.setSpouseID(person3.getPersonID());
        person3.setSpouseID(person2.getPersonID());

        person4.setSpouseID(person5.getPersonID());
        person5.setSpouseID(person4.getPersonID());

        person6.setSpouseID(person7.getPersonID());
        person7.setSpouseID(person6.getPersonID());

        ArrayList<ModelPersons> family = new ArrayList<>();
        family.add( person1);
        family.add( person2);
        family.add( person3);
        family.add( person4);
        family.add( person5);
        family.add( person6);
        family.add( person7);


        DataCache.getInstance().setPeopleMap(family);


        ModelPersons output = DataCache.getInstance().findSpouseByPersonID(person3);
        assertNotEquals(person6,output);
        assertNotEquals(person6, output);

    }



}
