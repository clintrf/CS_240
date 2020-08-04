package serviceClasses.requestService;

import java.util.PrimitiveIterator;

public class RequestLogin {
    private String userName;
    private String password;

    public RequestLogin(){
        this.userName = null;
        this.password = null;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }
}
