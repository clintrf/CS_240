package serviceClasses;

import java.sql.SQLException;
import java.util.ArrayList;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoPerson;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import modelClasses.*;

public class ResponsePeople {

    private ArrayList<ModelPerson> data = new ArrayList<>();
    private String message;
    private Boolean success;

    public void setData(ArrayList<ModelPerson> data){ this.data = data; }
    public void setMessage(String message){ this.message = message; }
    public void setSuccess(Boolean success) { this.success = success; }

    public ArrayList<ModelPerson> getData() { return this.data; }
    public String getMessage() { return this.message; }
    public Boolean getSuccess() { return this.success; }
}
