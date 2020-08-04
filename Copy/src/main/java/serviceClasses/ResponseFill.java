
package main.java.serviceClasses;

public class ResponseFill{

    private String message;
    private Boolean success;

    public void setMessage(String message){ this.message = message; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getMessage() { return this.message; }
    public Boolean getSuccess() { return this.success; }


}