package dataAccessClasses;

import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import modelClasses.ModelPerson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class personTest {

    DatabaseDatabase database;
    Connection conn;
    DaoPerson tempDao;
    ModelPerson tempModel01;
    ModelPerson tempModel02;
    ModelPerson tempModel03;

    @BeforeEach
    public void init() throws DatabaseException {
        this.database = new DatabaseDatabase();
        this.conn = database.openConnection();
        this.tempDao = new DaoPerson(conn);
        this.tempModel01 = new ModelPerson(
                "person_id_01",
                "associated_user_name_01",
                "first_name_01",
                "last_name_01",
                "m",
                "father_id_01",
                "mother_id_01",
                "spouse_id_01"
        );
        this.tempModel02 = new ModelPerson(
                "person_id_02",
                "associated_user_name_02",
                "first_name_02",
                "last_name_02",
                "m",
                "father_id_02",
                "mother_id_02",
                "spouse_id_02"
        );
        this.tempModel03 = new ModelPerson(
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
    public void finish() throws DatabaseException {
        tempDao.drop();
        database.closeConnection(false);
    }

    @Test
    public void createTest() throws DatabaseException {
        tempDao.create();
    }

    @Test
    public void clearTest() throws DatabaseException {
        tempDao.clear();
    }

    @Test
    public void dropTest() throws DatabaseException {
        tempDao.drop();
    }

    @Test
    public void insertTest() throws DatabaseException {
        ModelPerson tempModel_new = new ModelPerson(
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
                tempModel_new.getPersonId(),
                tempDao.findPersonById("person_id_new").getPersonId(),
                "person_id are not equal"
        );
    }

    @Test
    public void removePersonByIdTest() throws DatabaseException {
        tempDao.removePersonById("person_id_01");

        assertNull(tempDao.findPersonById("person_id_01"), "Was not removed");
    }

    @Test
    public void removePeopleByIds() throws DatabaseException {

        ArrayList<String> tempStringArray = new ArrayList<String>();
        tempStringArray.add("person_id_01");
        tempStringArray.add("person_id_02");

        tempDao.removePeopleByIds(tempStringArray);

        assertNull(tempDao.findPersonById("person_id_01"), "01 was not removed");
        assertNull(tempDao.findPersonById("person_id_02"), "02 was not removed");
    }

    @Test
    public void findPersonByIdTest() throws DatabaseException {

        assertEquals(
                tempModel01.getPersonId(),
                tempDao.findPersonById("person_id_01").getPersonId(),
                "Id's are not equal"
        );
        assertEquals(
                tempModel02.getPersonId(),
                tempDao.findPersonById("person_id_02").getPersonId(),
                "Id's are not equal"
        );
        assertEquals(
                tempModel03.getPersonId(),
                tempDao.findPersonById("person_id_03").getPersonId(),
                "Id's are not equal"
        );
    }

    @Test
    public void findPeopleByIdsTest() throws DatabaseException {
        ArrayList<String> tempStringArray = new ArrayList<String>();
        tempStringArray.add("person_id_01");
        tempStringArray.add("person_id_02");
        assertEquals(tempModel01.getPersonId(), tempDao.findPeopleByIds(tempStringArray).get(0).getPersonId(), "Multiple find not working");
        assertEquals(tempModel02.getPersonId(), tempDao.findPeopleByIds(tempStringArray).get(1).getPersonId(), "Multiple find not working");

    }

    @Test
    public void findPeopleByAssociatedUserNameTest() throws DatabaseException {
        assertEquals(
                tempModel01.getAssociatedUserName(),
                tempDao.findPeopleByAssociatedUserName("associated_user_name_01").get(0).getAssociatedUserName(),
                "find by username find not working");

    }
}
