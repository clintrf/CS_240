package dataAccessClasses;

import databaseClasses.*;
import modelClasses.ModelAuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BooleanSupplier;

public class authTokenTest {

    DatabaseDatabase database;
    Connection conn;
    DaoAuthToken tempDao;
    ModelAuthToken tempModel01;
    ModelAuthToken tempModel02;
    ModelAuthToken tempModel03;

    @BeforeEach
    public void init() throws DatabaseException {
        this.database = new DatabaseDatabase();
        this.conn = database.openConnection();
        this.tempDao = new DaoAuthToken(conn);
        this.tempModel01 = new ModelAuthToken(
                "authToken01",
                "username01",
                "password01"

        );
        this.tempModel02 = new ModelAuthToken(
                "authToken02",
                "username02",
                "password02"
        );
        this.tempModel03 = new ModelAuthToken(
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
        ModelAuthToken tempModel_new = new ModelAuthToken(
                "authToken_new",
                "username_new",
                "password_new"
        );
        tempDao.insert(tempModel_new);

        assertEquals(
                tempModel_new.getAuthToken(),
                tempDao.findAuthTokenByToken("authToken_new").getAuthToken(),
                "AuthTokens are not equal"
        );
    }

    @Test
    public void removeAuthTokenByTokenTest() throws DatabaseException {
        ModelAuthToken tempModel_new = new ModelAuthToken(
                "authToken_new",
                "username_new",
                "password_new"
        );
        tempDao.insert(tempModel_new);
        tempDao.removeAuthTokenByToken("authToken_new");

        assertNull(tempDao.findAuthTokenByToken("authToken_new"), "Was not removed");
    }

    @Test
    public void removeAuthTokensByTokensTest() throws DatabaseException {
        ModelAuthToken tempModel_new = new ModelAuthToken(
                "authToken_new",
                "username_new",
                "password_new"
        );
        ModelAuthToken tempModel_newer = new ModelAuthToken(
                "authToken_newer",
                "username_newer",
                "password_newer"
        );

        tempDao.insert(tempModel_new);
        tempDao.insert(tempModel_newer);

        ArrayList<String> tempStringArray = new ArrayList<String>();
        tempStringArray.add("authToken_new");
        tempStringArray.add("authToken_newer");

        tempDao.removeAuthTokensByTokens(tempStringArray);

        assertNull(tempDao.findAuthTokenByToken("authToken_new"), "New was not removed");
        assertNull(tempDao.findAuthTokenByToken("authToken_newer"), "Newer was not removed");
    }

    @Test
    public void findAuthTokenByTokenTest() throws DatabaseException {

        assertEquals(
                tempModel01.getAuthToken(),
                tempDao.findAuthTokenByToken("authToken01").getAuthToken(),
                "AuthTokens are not equal"
        );
        assertEquals(
                tempModel02.getAuthToken(),
                tempDao.findAuthTokenByToken("authToken02").getAuthToken(),
                "AuthTokens are not equal"
        );
        assertEquals(
                tempModel03.getAuthToken(),
                tempDao.findAuthTokenByToken("authToken03").getAuthToken(),
                "AuthTokens are not equal"
        );
    }

    @Test
    public void findAuthTokensByTokensTest() throws DatabaseException {
        ArrayList<String> tempStringArray = new ArrayList<String>();
        tempStringArray.add("authToken01");
        tempStringArray.add("authToken02");
        assertEquals(2, tempDao.findAuthTokensByTokens(tempStringArray).size(), "Multiple find not working");
    }

    @Test
    public void findAuthTokenByUserNameTest() throws DatabaseException {
        assertEquals(
                tempModel01.getUserName(),
                tempDao.findAuthTokenByUserName("username01").getUserName(),
                "AuthTokens are not equal"
        );
    }
}
