package familymap.server.serviceClasses;

public class RequestLogin{

    private String userName;
    private String password;

    private String host;
    private String port;

    public void setUserName(String userName) { this.userName = userName; }
    public void setPassword(String password) { this.password = password; }

    public String getUserName(){return this.userName;}
    public String getPassword() {return this.password;}

    public String getHost() {
        return host;
    }

    public void setHost(String toString) {
        this.host = toString;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String toString) {
        this.port = toString;
    }
}