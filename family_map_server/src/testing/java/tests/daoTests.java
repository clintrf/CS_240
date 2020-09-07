package testing.java.tests;

import com.google.gson.internal.bind.SqlDateTypeAdapter;
import main.java.daoClasses.*;
import main.java.modelClasses.ModelAuthTokens;
import main.java.modelClasses.ModelEvents;
import main.java.modelClasses.ModelPersons;
import main.java.modelClasses.ModelUsers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class authTokenTest {
        DatabaseDatabase database;
        Connection conn;
        DaoAuthToken tempDao;
        ModelAuthTokens tempModel01;
        ModelAuthTokens tempModel02;
        ModelAuthTokens tempModel03;

        @BeforeEach
        public void init() throws SQLException {
            this.database  = new DatabaseDatabase();
            this.conn = database.openConnection();
            this.tempDao = new DaoAuthToken(conn);
            tempDao.clear();
            this.tempModel01 = new ModelAuthTokens(
                    "authToken01",
                    "username01",
                    "password01"

            );
            this.tempModel02 = new ModelAuthTokens(
                    "authToken02",
                    "username02",
                    "password02"
            );
            this.tempModel03 = new ModelAuthTokens(
                    "authToken03",
                    "username03",
                    "password03"
            );

            tempDao.create();
            tempDao.insert(tempModel01);
            tempDao.insert(tempModel02);
            tempDao.insert(tempModel03);
        }

        @AfterEach
        public void finish() {
            try {
                tempDao.drop();
                assertTrue(true);
            } catch (SQLException e){
                e.getStackTrace();
                fail();
            }
        }

        @Test
        public void createTest() {
            try {
                tempDao.create();
                assertEquals(
                        "authToken01",
                        tempDao.getAuthTokenByToken("authToken01").getAuthToken(),
                        "AuthTokens are not equal"
                );
            } catch (SQLException e){
                e.getStackTrace();
                fail();
            }
        }

        @Test
        public void clearTest() throws SQLException {
            try {
                tempDao.clear();
                assertNotEquals(
                        "authToken01",
                        tempDao.getAuthTokenByToken("authToken01").getAuthToken(),
                        "AuthTokens are not equal"
                );
            } catch (SQLException | NullPointerException e){
                e.getStackTrace();
                assertTrue(true);
            }
        }

        @Test
        public void dropTest() {
            try {
                tempDao.drop();
                assertNotEquals(
                        "authToken01",
                        tempDao.getAuthTokenByToken("authToken01").getAuthToken(),
                        "AuthTokens are not equal"
                );
            } catch (SQLException | NullPointerException e){
                e.getStackTrace();
                assertTrue(true);
            }
        }

        @Test
        public void insertGoodTest() throws SQLException {

            ModelAuthTokens tempModel_new = new ModelAuthTokens(
                    "authToken_new",
                    "username_new",
                    "password_new"
            );
            tempDao.insert(tempModel_new);

            assertEquals(
                    tempModel_new.getAuthToken(),
                    tempDao.getAuthTokenByToken("authToken_new").getAuthToken(),
                    "AuthTokens are not equal"
            );
        }

        @Test
        public void insertFailTest() throws SQLException {
            try {
                ModelAuthTokens tempModel_new = new ModelAuthTokens(
                        "authToken_new",
                        "username_new",
                        "password_new"
                );
                tempDao.insert(tempModel_new);

                assertNotEquals(
                        tempModel_new.getAuthToken(),
                        tempDao.getAuthTokenByToken("authToken_neww").getAuthToken(),
                        "AuthTokens are equal with bad name"
                );
            } catch (NullPointerException e){
                e.printStackTrace();
                assertTrue(true);
            }
        }


        @Test
        public void findAuthTokenByTokenGoodTest() throws SQLException {

            assertEquals(
                    tempModel01.getAuthToken(),
                    tempDao.getAuthTokenByToken("authToken01").getAuthToken(),
                    "AuthTokens are not equal"
            );
            assertEquals(
                    tempModel02.getAuthToken(),
                    tempDao.getAuthTokenByToken("authToken02").getAuthToken(),
                    "AuthTokens are not equal"
            );
            assertEquals(
                    tempModel03.getAuthToken(),
                    tempDao.getAuthTokenByToken("authToken03").getAuthToken(),
                    "AuthTokens are not equal"
            );
        }

        @Test
        public void findAuthTokenByTokenFailTest() throws SQLException {
            try {
                assertNotEquals(
                        tempModel01.getAuthToken(),
                        tempDao.getAuthTokenByToken("authToken011").getAuthToken(),
                        "AuthTokens are equal"
                );
                assertNotEquals(
                        tempModel02.getAuthToken(),
                        tempDao.getAuthTokenByToken("authToken022").getAuthToken(),
                        "AuthTokens are equal"
                );
                assertNotEquals(
                        tempModel03.getAuthToken(),
                        tempDao.getAuthTokenByToken("authToken033").getAuthToken(),
                        "AuthTokens are equal"
                );
            } catch (NullPointerException e){
                e.printStackTrace();
                assertTrue(true);
            }
        }
    }


    class eventTest {

        DatabaseDatabase database;
        Connection conn;
        DaoEvent tempDao;
        ModelEvents tempModel01;
        ModelEvents tempModel02;
        ModelEvents tempModel03;

        @BeforeEach
        public void init() throws SQLException {
            this.database  = new DatabaseDatabase();
            this.conn = database.openConnection();
            this.tempDao = new DaoEvent(conn);
            tempDao.clear();

            this.tempModel01 = new ModelEvents(
                    "event_id_01",
                    "associated_user_name_01",
                    "person_id_01",
                    0.1,
                    0.1,
                    "country_01",
                    "city_01",
                    "event_type_01",
                    2001

            );
            this.tempModel02 = new ModelEvents(
                    "event_id_02",
                    "associated_user_name_02",
                    "person_id_02",
                    0.2,
                    0.2,
                    "country_02",
                    "city_02",
                    "event_type_02",
                    2002
            );
            this.tempModel03 = new ModelEvents(
                    "event_id_03",
                    "associated_user_name_03",
                    "person_id_03",
                    0.3,
                    0.3,
                    "country_03",
                    "city_03",
                    "event_type_03",
                    2001
            );

            tempDao.create();
            tempDao.insert(tempModel01);
            tempDao.insert(tempModel02);
            tempDao.insert(tempModel03);
        }

        @AfterEach
        public void finish() throws SQLException {
            tempDao.drop();
        }

        @Test
        public void createTest() {
            try {
                tempDao.create();
                assertEquals(
                        "event_id_01",
                        tempDao.getEventById("event_id_01").getEventID(),
                        "event_id_01 are not equal"
                );
            } catch (SQLException e){
                e.getStackTrace();
                fail();
            }
        }

        @Test
        public void clearTest() {
            try {
                tempDao.clear();
                assertNotEquals(
                        "event_id_01",
                        tempDao.getEventById("event_id_01").getEventID(),
                        "event_id_01 are not equal"
                );
            } catch (SQLException | NullPointerException e){
                e.getStackTrace();
                assertTrue(true);
            }
        }

        @Test
        public void dropGoodTest() {
            try {
                tempDao.drop();
                tempDao.getEventById("event_id_01").getEventID();
                fail();
            } catch (SQLException e){
                e.getStackTrace();
                assertTrue(true);
            }
        }

        @Test
        public void dropFailTest() {
            try {
                tempDao.drop();
                tempDao.getEventById("event_id_011").getEventID();
                fail();
            } catch (SQLException e){
                e.getStackTrace();
                assertTrue(true);
            }
        }

        @Test
        public void insertGoodTest() throws SQLException {
            ModelEvents tempModel_new = new ModelEvents(
                    "event_id_new",
                    "associated_user_name_new",
                    "person_id_new",
                    0.1,
                    0.1,
                    "country_new",
                    "city_new",
                    "event_type_new",
                    2001
            );
            try {
                tempDao.insert(tempModel_new);
                assertTrue(true);
            } catch (SQLException e){
                e.getStackTrace();
                fail();
            }

            assertEquals(
                    tempModel_new.getEventID(),
                    tempDao.getEventById("event_id_new").getEventID(),
                    "AuthTokens are not equal"
            );
        }
        @Test
        public void insertFailTest() {
            ModelEvents tempModel_new = new ModelEvents(
                    "event_id_new",
                    "associated_user_name_new",
                    null,
                    0.1,
                    0.1,
                    "country_new",
                    "city_new",
                    "event_type_new",
                    2001
            );
            try {
                tempDao.insert(tempModel_new);
                fail();
            } catch (SQLException e){
                e.getStackTrace();
                assertTrue(true);
            }
        }

        @Test
        public void removeEventByIdGoodTest() throws SQLException {
            tempDao.removeEventByAssociatedUsername("associated_user_name_01");

            assertNull(tempDao.getEventById("event_id_01"), "Was not removed");
        }

        @Test
        public void removeEventByIdFailTest() throws SQLException {
            tempDao.removeEventByAssociatedUsername("associated_user_name_011");
            assertNotNull(tempDao.getEventById("event_id_01"), "Given wrong ID event was removed ");
        }


        @Test
        public void getEventByIdGoodTest() throws SQLException {

            assertEquals(
                    tempModel01.getEventID(),
                    tempDao.getEventById("event_id_01").getEventID(),
                    "Id's are not equal"
            );
            assertEquals(
                    tempModel02.getEventID(),
                    tempDao.getEventById("event_id_02").getEventID(),
                    "Id's are not equal"
            );
            assertEquals(
                    tempModel03.getEventID(),
                    tempDao.getEventById("event_id_03").getEventID(),
                    "Id's are not equal"
            );
        }
        @Test
        public void getEventByIdFailTest() {
            try {
                assertNotEquals(
                        tempModel01.getEventID(),
                        tempDao.getEventById("event_id_011").getEventID(),
                        "Found event with wrong ID"
                );
                assertNotEquals(
                        tempModel02.getEventID(),
                        tempDao.getEventById("event_id_022").getEventID(),
                        "Found event with wrong ID"
                );
                assertNotEquals(
                        tempModel03.getEventID(),
                        tempDao.getEventById("event_id_033").getEventID(),
                        "Found event with wrong ID"
                );
            } catch (NullPointerException | SQLException ex){
                ex.getStackTrace();
                assertTrue(true); // could not find events by wrong ID
            }
        }

    }

    class personTest {

        DatabaseDatabase database;
        Connection conn;
        DaoPerson tempDao;
        ModelPersons tempModel01;
        ModelPersons tempModel02;
        ModelPersons tempModel03;

        @BeforeEach
        public void init() throws SQLException {
            this.database = new DatabaseDatabase();
            this.conn = database.openConnection();
            this.tempDao = new DaoPerson(conn);
            this.tempModel01 = new ModelPersons(
                    "person_id_01",
                    "associated_user_name_01",
                    "first_name_01",
                    "last_name_01",
                    "m",
                    "father_id_01",
                    "mother_id_01",
                    "spouse_id_01"
            );
            this.tempModel02 = new ModelPersons(
                    "person_id_02",
                    "associated_user_name_02",
                    "first_name_02",
                    "last_name_02",
                    "m",
                    "father_id_02",
                    "mother_id_02",
                    "spouse_id_02"
            );
            this.tempModel03 = new ModelPersons(
                    "person_id_03",
                    "associated_user_name_03",
                    "first_name_03",
                    "last_name_03",
                    "m",
                    "father_id_03",
                    "mother_id_03",
                    "spouse_id_03"
            );

            tempDao.create();
            tempDao.insert(tempModel01);
            tempDao.insert(tempModel02);
            tempDao.insert(tempModel03);
        }

        @AfterEach
        public void finish() throws SQLException {
            tempDao.drop();
        }

        @Test
        public void createTest() {
            try {
                tempDao.create();
                assertEquals(
                        "person_id_01",
                        tempDao.getPersonByID("person_id_01").getPersonID(),
                        "person_id are not equal"
                );
            } catch (SQLException e){
                e.getStackTrace();
                fail();
            }
        }

        @Test
        public void clearTest() {
            try {
                tempDao.clear();
                assertNotEquals(
                        "person_id_01",
                        tempDao.getPersonByID("person_id_01").getPersonID(),
                        "person_id are not equal"
                );
            } catch (SQLException | NullPointerException e){
                e.getStackTrace();
                assertTrue(true);
            }
        }

        @Test
        public void dropTest() {

            try {
                tempDao.drop();
                assertNotEquals(
                        "person_id_01",
                        tempDao.getPersonByID("person_id_01").getPersonID(),
                        "person_id are not equal"
                );

            } catch (SQLException e){
                e.getStackTrace();
                assertTrue(true);
            }
        }

        @Test
        public void insertGoodTest() throws SQLException {
            ModelPersons tempModel_new = new ModelPersons(
                    "person_id_new",
                    "associated_user_name_new",
                    "first_name_new",
                    "last_name_new",
                    "m",
                    "father_id_new",
                    "mother_id_new",
                    "spouse_id_new"
            );
            tempDao.insert(tempModel_new);

            assertEquals(
                    tempModel_new.getPersonID(),
                    tempDao.getPersonByID("person_id_new").getPersonID(),
                    "person_id are not equal"
            );
        }
        @Test
        public void insertFailTest() throws SQLException {
            ModelPersons tempModel_new = new ModelPersons(
                    null,
                    "associated_user_name_new",
                    "first_name_new",
                    "last_name_new",
                    "m",
                    "father_id_new",
                    "mother_id_new",
                    "spouse_id_new"
            );
            try {
                tempDao.insert(tempModel_new);
                fail();
            } catch (SQLException e){
                e.getStackTrace();
                assertTrue(true);
            }
        }

        @Test
        public void removePersonByIdGoodTest() throws SQLException {
            tempDao.removePersonByAssociatedUsername("associated_user_name_01");
            assertNull(tempDao.getPersonByID("person_id_01"), "Was not removed");
        }

        @Test
        public void removePersonByIdFailTest() throws SQLException {
            tempDao.removePersonByAssociatedUsername("associated_user_name_011");
            assertNotNull(tempDao.getPersonByID("person_id_01"), "person was removed with bad ID");
        }

        @Test
        public void removePeopleByIdsGood() throws SQLException {

            ArrayList<String> tempStringArray = new ArrayList<String>();
            tempStringArray.add("associated_user_name_01");
            tempStringArray.add("associated_user_name_02");

            tempDao.removePersonByAssociatedUsername(tempStringArray.get(0));
            tempDao.removePersonByAssociatedUsername(tempStringArray.get(1));

            assertNull(tempDao.getPersonByID("person_id_01"), "01 was not removed");
            assertNull(tempDao.getPersonByID("person_id_02"), "02 was not removed");
        }
        @Test
        public void removePeopleByIdsFail() throws SQLException {

            ArrayList<String> tempStringArray = new ArrayList<String>();
            tempStringArray.add("associated_user_name_011");
            tempStringArray.add("associated_user_name_022");

            tempDao.removePersonByAssociatedUsername(tempStringArray.get(0));
            tempDao.removePersonByAssociatedUsername(tempStringArray.get(1));

            assertNotNull(tempDao.getPersonByID("person_id_01"), "01 was removed from bad id");
            assertNotNull(tempDao.getPersonByID("person_id_02"), "02 was removed from bad id");
        }

        @Test
        public void findPersonByIdTest() throws SQLException {
            try {
                assertNotEquals(
                        tempModel01.getPersonID(),
                        tempDao.getPersonByID("person_id_011").getPersonID(),
                        "Id's are equal"
                );
                assertNotEquals(
                        tempModel02.getPersonID(),
                        tempDao.getPersonByID("person_id_022").getPersonID(),
                        "Id's are equal"
                );
                assertNotEquals(
                        tempModel03.getPersonID(),
                        tempDao.getPersonByID("person_id_033").getPersonID(),
                        "Id's are equal"
                );
            } catch (NullPointerException e){
                e.printStackTrace();
                assertTrue(true);
            }
        }

        @Test
        public void findPeopleByIdsGoodTest() throws SQLException {
            ArrayList<String> tempStringArray = new ArrayList<String>();
            tempStringArray.add("person_id_01");
            tempStringArray.add("person_id_02");
            assertEquals(tempModel01.getPersonID(), tempDao.getPersonByID(tempStringArray.get(0)).getPersonID(), "Multiple find not working");
            assertEquals(tempModel02.getPersonID(), tempDao.getPersonByID(tempStringArray.get(1)).getPersonID(), "Multiple find not working");

        }

        @Test
        public void findPeopleByIdsFailTest() throws SQLException {
            try {
                ArrayList<String> tempStringArray = new ArrayList<String>();
                tempStringArray.add("person_id_011");
                tempStringArray.add("person_id_022");
                assertNotEquals(tempModel01.getPersonID(), tempDao.getPersonByID(tempStringArray.get(0)).getPersonID(), "Multiple find not working");
                assertNotEquals(tempModel02.getPersonID(), tempDao.getPersonByID(tempStringArray.get(1)).getPersonID(), "Multiple find not working");
            } catch(NullPointerException e){
                e.printStackTrace();
                assertTrue(true);
            }

        }

        @Test
        public void findPeopleByAssociatedUserNameGoodTest() throws SQLException {
            assertEquals(
                    tempModel01.getAssociatedUsername(),
                    tempDao.getPeopleByAssociatedUsername("associated_user_name_01").get(0).getAssociatedUsername(),
                    "find by username find not working");

        }

        @Test
        public void findPeopleByAssociatedUserNameFailTest() throws SQLException {
            try {
                assertNotEquals(
                        tempModel01.getAssociatedUsername(),
                        tempDao.getPeopleByAssociatedUsername("associated_user_name_011").get(0).getAssociatedUsername(),
                        "find by username find not working");

            }catch (NullPointerException | IndexOutOfBoundsException e){
                e.printStackTrace();
                assertTrue(true);
            }
        }
    }

    class userTest {
        DatabaseDatabase database;
        Connection conn;
        DaoUser tempDao;
        ModelUsers tempModel01;
        ModelUsers tempModel02;
        ModelUsers tempModel03;

        @BeforeEach
        public void init() throws SQLException {
            this.database = new DatabaseDatabase();
            this.conn = database.openConnection();
            this.tempDao = new DaoUser(conn);
            this.tempModel01 = new ModelUsers(
                    "user_name_01",
                    "password_01",
                    "email_01",
                    "first_name_01",
                    "last_name_01",
                    "m",
                    "person_id_01"
            );
            this.tempModel02 = new ModelUsers(
                    "user_name_02",
                    "password_02",
                    "email_02",
                    "first_name_02",
                    "last_name_02",
                    "m",
                    "person_id_02"
            );
            this.tempModel03 = new ModelUsers(
                    "user_name_03",
                    "password_03",
                    "email_03",
                    "first_name_03",
                    "last_name_03",
                    "m",
                    "person_id_03"
            );

            tempDao.create();
            tempDao.insert(tempModel01);
            tempDao.insert(tempModel02);
            tempDao.insert(tempModel03);
        }

        @AfterEach
        public void finish() throws SQLException {
            tempDao.drop();
        }

        @Test
        public void createTest() throws SQLException {
            try{
                tempDao.create();
                assertEquals(
                        "user_name_01",
                        tempDao.getUserByUsername("user_name_01").getUserName(),
                        "user_name are not equal"
                );
            } catch (NullPointerException | SQLException e){
                e.printStackTrace();
                fail();
            }

        }

        @Test
        public void clearTest() throws SQLException {

            try{
                tempDao.clear();
                assertNotEquals(
                        "user_name_01",
                        tempDao.getUserByUsername("user_name_01").getUserName(),
                        "user_name are not equal"
                );
            } catch (NullPointerException | SQLException e){
                e.printStackTrace();
                assertTrue(true);
            }
        }

        @Test
        public void dropTest() throws SQLException {
            try{
                tempDao.drop();
                assertNotEquals(
                        "user_name_01",
                        tempDao.getUserByUsername("user_name_01").getUserName(),
                        "user_name are not equal"
                );
            } catch (NullPointerException | SQLException e){
                e.printStackTrace();
                assertTrue(true);
            }
        }

        @Test
        public void insertGoodTest() throws SQLException {
            ModelUsers tempModel_new = new ModelUsers(
                    "user_name_new",
                    "password_new",
                    "email_new",
                    "first_name_new",
                    "last_name_new",
                    "m",
                    "person_id_new"
            );
            tempDao.insert(tempModel_new);

            assertEquals(
                    tempModel_new.getUserName(),
                    tempDao.getUserByUsername("user_name_new").getUserName(),
                    "user_name are not equal"
            );
        }

        @Test
        public void insertFailTest() {
            ModelUsers tempModel_new = new ModelUsers(
                    null,
                    "password_new",
                    "email_new",
                    "first_name_new",
                    "last_name_new",
                    "m",
                    "person_id_new"
            );
            try {
                tempDao.insert(tempModel_new);

                assertNotEquals(
                        tempModel_new.getUserName(),
                        tempDao.getUserByUsername("user_name_news").getUserName(),
                        "user_name are equal"
                );
            }catch (NullPointerException | SQLException e){
                e.printStackTrace();
                assertTrue(true);
            }
        }


        @Test
        public void findUserByUserNameGoodTest() throws SQLException {

            assertEquals(
                    tempModel01.getUserName(),
                    tempDao.getUserByUsername("user_name_01").getUserName(),
                    "Id's are not equal"
            );
            assertEquals(
                    tempModel02.getUserName(),
                    tempDao.getUserByUsername("user_name_02").getUserName(),
                    "Id's are not equal"
            );
            assertEquals(
                    tempModel03.getUserName(),
                    tempDao.getUserByUsername("user_name_03").getUserName(),
                    "Id's are not equal"
            );
        }

        @Test
        public void findUserByUserNameFailTest() throws SQLException {
            try {
                assertNotEquals(
                        tempModel01.getUserName(),
                        tempDao.getUserByUsername("user_name_011").getUserName(),
                        "Id's are  equal"
                );
                assertNotEquals(
                        tempModel02.getUserName(),
                        tempDao.getUserByUsername("user_name_022").getUserName(),
                        "Id's are  equal"
                );
                assertNotEquals(
                        tempModel03.getUserName(),
                        tempDao.getUserByUsername("user_name_033").getUserName(),
                        "Id's are  equal"
                );
            } catch (SQLException | NullPointerException e){
                e.printStackTrace();
                assertTrue(true);
            }
        }

        @Test
        public void findUsersByUserNamesGoodTest() throws SQLException {
            ArrayList<String> tempStringArray = new ArrayList<String>();
            tempStringArray.add("user_name_01");
            tempStringArray.add("user_name_02");
            assertEquals(tempModel01.getUserName(), tempDao.getUserByUsername(tempStringArray.get(0)).getUserName(), "Multiple find not working");
            assertEquals(tempModel02.getUserName(), tempDao.getUserByUsername(tempStringArray.get(1)).getUserName(), "Multiple find not working");

        }

        @Test
        public void findUsersByUserNamesFailTest() throws SQLException {
            try {
                ArrayList<String> tempStringArray = new ArrayList<String>();
                tempStringArray.add("user_name_011");
                tempStringArray.add("user_name_022");
                assertNotEquals(tempModel01.getUserName(), tempDao.getUserByUsername(tempStringArray.get(0)).getUserName(), "Multiple find not working");
                assertNotEquals(tempModel02.getUserName(), tempDao.getUserByUsername(tempStringArray.get(1)).getUserName(), "Multiple find not working");
            } catch (SQLException | NullPointerException e){
                e.printStackTrace();
                assertTrue(true);
            }

        }

    }


