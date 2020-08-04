
package main.java.serviceClasses;

import java.util.*;

import main.java.modelClasses.ModelPersons;

public class ResponsePeople{

    private ArrayList<ModelPersons> data = new ArrayList<>();
    private String message;
    private Boolean success;

    public void setData(ArrayList<ModelPersons> data){ this.data = data; }
    public void setMessage(String message){ this.message = message; }
    public void setSuccess(Boolean success) { this.success = success; }

    public ArrayList<ModelPersons> getData() { return this.data; }
    public String getMessage() { return this.message; }
    public Boolean getSuccess() { return this.success; }
}