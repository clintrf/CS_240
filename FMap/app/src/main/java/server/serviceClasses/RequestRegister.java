package server.serviceClasses;

public class RequestRegister{

    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;

    private String host;
    private String port;

    public void setUserName(String userName){ this.userName = userName; }
    public void setPassword(String password){ this.password = password; }
    public void setEmail(String email){ this.email = email; }
    public void setFirstName(String firstName){ this.firstName = firstName; }
    public void setLastName(String lastName){ this.lastName = lastName; }
    public void setGender(String gender){ this.gender = gender; }

    public String getUserName() { return this.userName; }
    public String getPassword() { return this.password; }
    public String getEmail() { return this.email; }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public String getGender() { return this.gender; }

    public void setHost(String toString) {
        this.host = toString;
    }

    public void setPort(String toString) {
        this.port = toString;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }
}