package serviceClasses.resultService;

import java.sql.Connection;
import java.util.ArrayList;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoPerson;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import handlerClasses.EncoderDecoder;
import handlerClasses.Handler;
import modelClasses.*;

public class ResultsPeople {

    private DaoAuthToken tokenDao;
    private DaoPerson personDao;

    private ArrayList<ModelPerson> data;
    private Boolean success;
    private String message;

    public ResultsPeople(DatabaseDatabase database) {
        this.tokenDao = database.getTokenDao();
        this.personDao = database.getPersonDao();

        setData(null);
        setSuccess(false);
        setMessage("Fail");
    }

    public void peopleResult(String auth_token) throws DatabaseException {
        try {
            ModelAuthToken tokenObj = tokenDao.findAuthTokenByToken(auth_token);
            try { ArrayList<ModelPerson> peopleObj = personDao.findPeopleByAssociatedUserName(tokenObj.getUserName());
                if (peopleObj.size() == 0 ){
                    setMessage("no people found");
                    setSuccess(false);
                    return;
                }
                setData(peopleObj);
                setMessage("success in resultsPeople");
                setSuccess(true);

            } catch (DatabaseException e) {
                setMessage("Persons not found");
                setSuccess(false);
                e.printStackTrace();
            }
        } catch (DatabaseException e) {
            setMessage("token not found/ invalid token");
            setSuccess(false);
            e.printStackTrace();
        }

        this.data = new ArrayList<>();
        this.success = false;
        this.message = "Error";
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
