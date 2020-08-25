package familymap.server.serviceClasses;

import java.sql.Connection;
import java.sql.SQLException;

import familymap.server.daoClasses.DaoAuthToken;
import familymap.server.daoClasses.DaoEvent;
import familymap.server.daoClasses.DaoPerson;
import familymap.server.daoClasses.DaoUser;

public class ResponseClear{

    private String message;
    private Boolean success;

    public void setMessage(String message){ this.message = message; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getMessage() { return this.message; }
    public Boolean getSuccess() { return this.success; }


    public static ResponseClear clear(Connection conn){
        ResponseClear clearResponse = new ResponseClear();

        DaoAuthToken tokenDao = new DaoAuthToken(conn);
        DaoUser userDao = new DaoUser(conn);
        DaoEvent eventDao = new DaoEvent(conn);
        DaoPerson personDao = new DaoPerson(conn);

        try {
            tokenDao.drop();
            userDao.drop();
            eventDao.drop();
            personDao.drop();

            tokenDao.create();
            userDao.create();
            eventDao.create();
            personDao.create();

            clearResponse.setMessage("Clear succeeded.");
            clearResponse.setSuccess(true);
            return clearResponse;

        } catch (SQLException e) {
            clearResponse.setMessage("Clear failed.");
            clearResponse.setSuccess(false);
            return clearResponse;
        }



    }

}