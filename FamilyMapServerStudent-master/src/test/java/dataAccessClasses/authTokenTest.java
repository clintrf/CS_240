package dataAccessClasses;

import databaseClasses.*;
import modelClasses.ModelAuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serviceClasses.Services;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
/*
public class authTokenTest {
    //Services services = new Services();

    DatabaseDatabase database;
    Connection conn;
    DaoAuthToken tempDao;
    ModelAuthToken tempModel01;
    ModelAuthToken tempModel02;
    ModelAuthToken tempModel03;

    public authTokenTest() throws DatabaseException {
    }

    @BeforeEach
    public void init() throws DatabaseException, SQLException {
        this.database  = new DatabaseDatabase();
        this.conn = database.openConnection();
        //this.database = services.getDatabase();
        //this.tempDao = services.getDatabase().getTokenDao();
        this.tempDao = new DaoAuthToken();
        tempDao.clear(conn);
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

        tempDao.create(conn);
        tempDao.insert(tempModel01,conn);
        tempDao.insert(tempModel02,conn);
        tempDao.insert(tempModel03,conn);
    }

    @AfterEach
    public void finish() throws DatabaseException {
        tempDao.drop(conn);
        database.closeConnection(true);
    }

    @Test
    public void createTest() throws DatabaseException{
        tempDao.create(conn);
    }

    @Test
    public void clearTest() throws DatabaseException{
        tempDao.clear(conn);
    }

    @Test
    public void dropTest() throws DatabaseException {
        tempDao.drop(conn);
    }

    @Test
    public void insertTest() throws DatabaseException, SQLException {
        ModelAuthToken tempModel_new = new ModelAuthToken(
                "authToken_new",
                "username_new",
                "password_new"
        );
        tempDao.insert(tempModel_new, conn);

        assertEquals(
                tempModel_new.getAuthToken(),
                tempDao.getAuthTokenByToken("authToken_new",conn).getAuthToken(),
                "AuthTokens are not equal"
        );
    }

    @Test
    public void removeAuthTokenByTokenTest() throws DatabaseException, SQLException {
        ModelAuthToken tempModel_new = new ModelAuthToken(
                "authToken_new",
                "username_new",
                "password_new"
        );
        tempDao.insert(tempModel_new,conn);
        tempDao.removeAuthTokenByToken("authToken_new",conn);

        assertNull(tempDao.getAuthTokenByToken("authToken_new",conn), "Was not removed");
    }

    @Test
    public void removeAuthTokensByTokensTest() throws DatabaseException, SQLException {
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

        tempDao.insert(tempModel_new,conn);
        tempDao.insert(tempModel_newer,conn);

        ArrayList<String> tempStringArray = new ArrayList<String>();
        tempStringArray.add("authToken_new");
        tempStringArray.add("authToken_newer");

        tempDao.removeAuthTokensByTokens(tempStringArray,conn);

        assertNull(tempDao.getAuthTokenByToken("authToken_new",conn), "New was not removed");
        assertNull(tempDao.getAuthTokenByToken("authToken_newer",conn), "Newer was not removed");
    }

    @Test
    public void findAuthTokenByTokenTest() throws DatabaseException, SQLException {

        assertEquals(
                tempModel01.getAuthToken(),
                tempDao.getAuthTokenByToken("authToken01",conn).getAuthToken(),
                "AuthTokens are not equal"
        );
        assertEquals(
                tempModel02.getAuthToken(),
                tempDao.getAuthTokenByToken("authToken02",conn).getAuthToken(),
                "AuthTokens are not equal"
        );
        assertEquals(
                tempModel03.getAuthToken(),
                tempDao.getAuthTokenByToken("authToken03",conn).getAuthToken(),
                "AuthTokens are not equal"
        );
    }

    @Test
    public void findAuthTokensByTokensTest() throws DatabaseException, SQLException {
        ArrayList<String> tempStringArray = new ArrayList<String>();
        tempStringArray.add("authToken01");
        tempStringArray.add("authToken02");
        assertEquals(2, tempDao.findAuthTokensByTokens(tempStringArray,conn).size(), "Multiple find not working");
    }

    @Test
    public void findAuthTokenByUserNameTest() throws DatabaseException {
        assertEquals(
                tempModel01.getUserName(),
                tempDao.findAuthTokenByUserName("username01",conn).getUserName(),
                "AuthTokens are not equal"
        );
    }
}

*/