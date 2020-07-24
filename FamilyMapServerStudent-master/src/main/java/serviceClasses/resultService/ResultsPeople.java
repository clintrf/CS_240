package serviceClasses.resultService;

import java.util.ArrayList;
import modelClasses.*;

public class ResultsPeople {

    private ArrayList<ModelPerson> data;
    private Boolean success;
    private String message;

    public ResultsPeople(){
        this.data = new ArrayList<ModelPerson>();
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
