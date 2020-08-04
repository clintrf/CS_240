package dataAccessClasses;

import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import modelClasses.ModelUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serviceClasses.Services;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class userTest {

    Services services = new Services();

    DatabaseDatabase database;
    Connection conn;
    DaoUser tempDao;
    ModelUser tempModel01;
    ModelUser tempModel02;
    ModelUser tempModel03;

    public userTest() throws DatabaseException {
    }
/*
    @BeforeEach
    public void init() throws DatabaseException {
        this.database = services.getDatabase();
        this.tempDao = services.getDatabase().getUserDao();
        this.tempModel01 = new ModelUser(
                "user_name_01",
                "password_01",
                "email_01",
                "first_name_01",
                "last_name_01",
                "m",
                "person_id_01"
        );
        this.tempModel02 = new ModelUser(
                "user_name_02",
                "password_02",
                "email_02",
                "first_name_02",
                "last_name_02",
                "m",
                "person_id_02"
        );
        this.tempModel03 = new ModelUser(
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
    public void finish() throws DatabaseException {
        tempDao.drop();
        //database.closeConnection(false);
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
        ModelUser tempModel_new = new ModelUser(
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
                tempDao.findUserByUserName("user_name_new").getUserName(),
                "user_name are not equal"
        );
    }

    @Test
    public void removeUserByUserNameTest() throws DatabaseException {
        tempDao.removeUserByUserName("user_name_01");

        assertNull(tempDao.findUserByUserName("user_name_01").getUserName(), "Was not removed");
    }

    @Test
    public void removeUsersByUserNamesTest() throws DatabaseException {

        ArrayList<String> tempStringArray = new ArrayList<String>();
        tempStringArray.add("user_name_01");
        tempStringArray.add("user_name_02");

        tempDao.removeUsersByUserNames(tempStringArray);

        assertNull(tempDao.findUserByUserName("user_name_01").getUserName(), "01 was not removed");
        assertNull(tempDao.findUserByUserName("user_name_02").getUserName(), "02 was not removed");
    }

    @Test
    public void findUserByUserNameTest() throws DatabaseException {

        assertEquals(
                tempModel01.getUserName(),
                tempDao.findUserByUserName("user_name_01").getUserName(),
                "Id's are not equal"
        );
        assertEquals(
                tempModel02.getUserName(),
                tempDao.findUserByUserName("user_name_02").getUserName(),
                "Id's are not equal"
        );
        assertEquals(
                tempModel03.getUserName(),
                tempDao.findUserByUserName("user_name_03").getUserName(),
                "Id's are not equal"
        );
    }

    @Test
    public void findUsersByUserNamesTest() throws DatabaseException {
        ArrayList<String> tempStringArray = new ArrayList<String>();
        tempStringArray.add("user_name_01");
        tempStringArray.add("user_name_02");
        assertEquals(tempModel01.getUserName(), tempDao.findUsersByUserNames(tempStringArray).get(0).getUserName(), "Multiple find not working");
        assertEquals(tempModel02.getUserName(), tempDao.findUsersByUserNames(tempStringArray).get(1).getUserName(), "Multiple find not working");

    }

 */
}
