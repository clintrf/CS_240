
package main.java.serviceClasses;

import java.util.*;

import main.java.modelClasses.ModelEvents;

public class ResponseEvents{

    private ArrayList<ModelEvents> data = new ArrayList<>();
    private String message;
    private Boolean success;

    public void setData(ArrayList<ModelEvents> data){ this.data = data; }
    public void setMessage(String message){ this.message = message; }
    public void setSuccess(Boolean success) { this.success = success; }

    public ArrayList<ModelEvents> getData() { return this.data; }
    public String getMessage() { return this.message; }
    public Boolean getSuccess() { return this.success; }


}