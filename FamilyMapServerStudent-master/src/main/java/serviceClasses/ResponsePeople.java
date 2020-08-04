package serviceClasses;

import java.sql.SQLException;
import java.util.ArrayList;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoPerson;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import modelClasses.*;

public class ResponsePeople {

    private DatabaseDatabase database;
    private DaoAuthToken tokenDao;
    private DaoPerson personDao;

    private ArrayList<ModelPerson> data;
    private Boolean success;
    private String message;

    public ResponsePeople(DatabaseDatabase database) {
        this.database = database;
        this.tokenDao = database.getTokenDao();
        this.personDao = database.getPersonDao();

        setData(null);
        setSuccess(false);
        setMessage("Fail");
    }

    public ResponsePeople(String auth_token) throws DatabaseException {
        peopleResult(auth_token);
    }

    public void peopleResult(String auth_token) throws DatabaseException {
        try {
            ModelAuthToken tokenObj = tokenDao.getAuthTokenByToken(auth_token, database.getConnection());
            if(tokenObj.getUserName() == null){
                setData(null);
                setMessage("token not found/ invalid token");
                setSuccess(false);
                return;
            }

            ArrayList<ModelPerson> data = personDao.findPeopleByAssociatedUserName(tokenObj.getUserName(), database.getConnection());
            if(data == null) {
                setData(null);
                setMessage("Data not found");
                setSuccess(false);
                return;
            }

            if (data.size() == 0 ){
                setData(null);
                setMessage("no people found");
                setSuccess(false);
                return;
            }
            setData(data);
            setMessage("success in resultsPeople");
            setSuccess(true);

        } catch (DatabaseException | SQLException e) {
            setData(null);
            setMessage("token not found/ invalid token");
            setSuccess(false);
            e.printStackTrace();
        }
    }



    public void setData(ArrayList<ModelPerson> data) {
        this.data = data;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public ArrayList<ModelPerson> getData() {
        return this.data;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }
}
