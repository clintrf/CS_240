package serviceClasses;

import java.sql.SQLException;
import java.util.ArrayList;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoEvent;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import modelClasses.*;

public class ResponseEvents {
    private ArrayList<ModelEvent> data = new ArrayList<>();
    private String message;
    private Boolean success;

    public void setData(ArrayList<ModelEvent> data){ this.data = data; }
    public void setMessage(String message){ this.message = message; }
    public void setSuccess(Boolean success) { this.success = success; }

    public ArrayList<ModelEvent> getData() { return this.data; }
    public String getMessage() { return this.message; }
    public Boolean getSuccess() { return this.success; }


}