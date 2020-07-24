
package dataAccessClasses;

import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import modelClasses.ModelEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class eventTest {

    DatabaseDatabase database;
    Connection conn;
    DaoEvent tempDao;
    ModelEvent tempModel01;
    ModelEvent tempModel02;
    ModelEvent tempModel03;

    @BeforeEach
    public void init() throws DatabaseException {
        this.database = new DatabaseDatabase();
        this.conn = database.openConnection();
        this.tempDao = new DaoEvent(conn);
        this.tempModel01 = new ModelEvent(
                "event_id_01",
                "associated_user_name_01",
                "person_id_01" ,
                0.1 ,
                0.1 ,
                "country_01" ,
                "city_01" ,
                "event_type_01" ,
                2001

        );
        this.tempModel02 = new ModelEvent(
                "event_id_02",
                "associated_user_name_02",
                "person_id_02" ,
                0.2 ,
                0.2 ,
                "country_02" ,
                "city_02" ,
                "event_type_02" ,
                2002
        );
        this.tempModel03 = new ModelEvent(
                "event_id_03",
                "associated_user_name_03",
                "person_id_03" ,
                0.3 ,
                0.3 ,
                "country_03" ,
                "city_03" ,
                "event_type_03" ,
                2001
        );

        tempDao.create();
        tempDao.insert(tempModel01);
        tempDao.insert(tempModel02);
        tempDao.insert(tempModel03);
    }

    @AfterEach
    public void finish() throws DatabaseException {
        tempDao.drop();
        database.closeConnection(false);
    }

    @Test
    public void createTest() throws DatabaseException{
        tempDao.create();
    }

    @Test
    public void clearTest() throws DatabaseException{
        tempDao.clear();
    }

    @Test
    public void dropTest() throws DatabaseException {
        tempDao.drop();
    }

    @Test
    public void insertTest() throws DatabaseException{
        ModelEvent tempModel_new = new ModelEvent(
                "event_id_new",
                "associated_user_name_new",
                "person_id_new" ,
                0.1 ,
                0.1 ,
                "country_new" ,
                "city_new" ,
                "event_type_new" ,
                2001
        );
        tempDao.insert(tempModel_new);

        assertEquals(
                tempModel_new.getEventId(),
                tempDao.findEventById("event_id_new").getEventId(),
                "AuthTokens are not equal"
        );
    }

    @Test
    public void removeEventByIdTest() throws DatabaseException {
        tempDao.removeEventById("event_id_01");

        assertNull(tempDao.findEventById("event_id_01"), "Was not removed");
    }

    @Test
    public void removeEventsByIdsTest() throws DatabaseException {

        ArrayList<String> tempStringArray = new ArrayList<String>();
        tempStringArray.add("event_id_01");
        tempStringArray.add("event_id_02");

        tempDao.removeEventsByIds(tempStringArray);

        assertNull(tempDao.findEventById("event_id_01"), "01 was not removed");
        assertNull(tempDao.findEventById("event_id_02"), "02 was not removed");
    }

    @Test
    public void findEventByIdTest() throws DatabaseException {

        assertEquals(
                tempModel01.getEventId(),
                tempDao.findEventById("event_id_01").getEventId(),
                "Id's are not equal"
        );
        assertEquals(
                tempModel02.getEventId(),
                tempDao.findEventById("event_id_02").getEventId(),
                "Id's are not equal"
        );
        assertEquals(
                tempModel03.getEventId(),
                tempDao.findEventById("event_id_03").getEventId(),
                "Id's are not equal"
        );
    }

    @Test
    public void findEventsByIdsTest() throws DatabaseException {
        ArrayList<String> tempStringArray = new ArrayList<String>();
        tempStringArray.add("event_id_01");
        tempStringArray.add("event_id_02");
        assertEquals(tempModel01.getEventId(), tempDao.findEventsByIds(tempStringArray).get(0).getEventId(), "Multiple find not working");
        assertEquals(tempModel02.getEventId(), tempDao.findEventsByIds(tempStringArray).get(1).getEventId(), "Multiple find not working");

    }

    @Test
    public void findEventsByAssociatedUserNameTest() throws DatabaseException {
        assertEquals(
                tempModel01.getAssociatedUserName(),
                tempDao.findEventsByAssociatedUserName("associated_user_name_01").get(0).getAssociatedUserName(),
                "find by username find not working");

    }
}