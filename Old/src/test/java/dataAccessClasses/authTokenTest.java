package dataAccessClasses;

import databaseClasses.*;
import modelClasses.ModelAuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serviceClasses.Services;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.ArrayList;

public class authTokenTest {
    Services services = new Services();

    DatabaseDatabase database;
    Connection conn;
    DaoAuthToken tempDao;
    ModelAuthToken tempModel01;
    ModelAuthToken tempModel02;
    ModelAuthToken tempModel03;

    public authTokenTest() throws DatabaseException {
    }
/*
    @BeforeEach
    public void init() throws DatabaseException {

        this.database = services.getDatabase();
        this.tempDao = services.getDatabase().getTokenDao();
        tempDao.clear();
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
        //database.closeConnection(false);
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
                tempDao.getAuthTokenByToken("authToken_new").getAuthToken(),
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

        assertNull(tempDao.getAuthTokenByToken("authToken_new").getUserName(), "Was not removed");
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

        assertTrue(tempDao.getAuthTokenByToken("authToken_new").getAuthToken()==null, "New was not removed");
        assertTrue(tempDao.getAuthTokenByToken("authToken_newer").getAuthToken()==null, "Newer was not removed");
    }

    @Test
    public void findAuthTokenByTokenTest() throws DatabaseException {

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
    public void findAuthTokensByTokensTest() throws DatabaseException {
        ArrayList<String> tempStringArray = new ArrayList<String>();
        tempStringArray.add("authToken01");
        tempStringArray.add("authToken02");
        assertEquals(2, tempDao.getAuthTokensByTokens(tempStringArray).size(), "Multiple find not working");
    }

    @Test
    public void findAuthTokenByUserNameTest() throws DatabaseException {
        assertEquals(
                tempModel01.getUserName(),
                tempDao.getAuthTokenByUserName("username01").getUserName(),
                "AuthTokens are not equal"
        );
    }

 */
}
