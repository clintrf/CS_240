package server.serviceClasses;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import server.daoClasses.DaoAuthToken;
import server.daoClasses.DaoPerson;
import server.modelClasses.ModelAuthTokens;
import server.modelClasses.ModelPersons;

public class ResponsePeople{

    private ArrayList<ModelPersons> data;
    private String message;
    private Boolean success;

    public void setData(ArrayList<ModelPersons> data){ this.data = data; }
    public void setMessage(String message){ this.message = message; }
    public void setSuccess(Boolean success) { this.success = success; }

    public ArrayList<ModelPersons> getData() { return this.data; }
    public String getMessage() { return this.message; }
    public Boolean getSuccess() { return this.success; }

    public static ResponsePeople people(String authToken, Connection conn) {
        ResponsePeople peopleResponse = new ResponsePeople();

        DaoAuthToken authTokenDao = new DaoAuthToken(conn);
        DaoPerson personDao = new DaoPerson(conn);

        ArrayList<ModelPersons> data;
        ModelAuthTokens authTokenModel;

        try {
            authTokenModel = authTokenDao.getAuthTokenByToken(authToken);
            if (authTokenModel == null) {
                throw new SQLException();
            }

            data = personDao.getPeopleByAssociatedUsername(authTokenModel.getUserName());
            if (data == null){
                throw new SQLException();
            }
            if (data.size() == 0) {
                throw new SQLException();
            }

            peopleResponse.setData(data);
            peopleResponse.setSuccess(true);
            return peopleResponse;

        } catch (SQLException e) {
            peopleResponse.setData(null);
            peopleResponse.setMessage("error");
            peopleResponse.setSuccess(false);
            return peopleResponse;
        }
    }
}