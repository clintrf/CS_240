package serviceClasses;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoEvent;
import dataAccessClasses.DaoPerson;
import dataAccessClasses.DaoUser;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import modelClasses.ModelEvent;
import modelClasses.ModelPerson;
import modelClasses.ModelUser;
import serviceClasses.RequestLoad;

import javax.xml.crypto.Data;
import java.sql.SQLException;


public class ResponseLoad {
    private String message;
    private Boolean success;

    public void setMessage(String message){ this.message = message; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getMessage() { return this.message; }
    public Boolean getSuccess() { return this.success; }
}
