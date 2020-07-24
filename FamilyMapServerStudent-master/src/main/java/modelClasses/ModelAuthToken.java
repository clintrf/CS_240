package modelClasses;

import java.util.ArrayList;

/**
 * authTokenModel represents a row in the authToken table and its variables relate to the columns in the row
 */
public class ModelAuthToken {

    /**
     * List of authTokens
     */
    private String authToken;

    /**
     * Unique user name (non-empty string)
     */
    private String userName;

    /**
     * User’s password (non-empty string)
     */
    private String password;




    /**
     * Constructor for authTokenModel. Inits all user vars
     * @param authToken List of authTokens
     * @param userName Unique user name (non-empty string)
     * @param password User’s password (non-empty string)
     */
    public ModelAuthToken(
            String authToken,
            String userName,
            String password){
        this.authToken = authToken;
        this.userName = userName;
        this.password = password;

    }


    /**
     * Sets the authToken variable
     * @param authToken List of authTokens
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Sets the userName variable
     * @param userName Unique user name (non-empty string)
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets the password variable
     * @param password User’s password (non-empty string)
     */
    public void setPassword(String password){
        this.password = password;
    }




    /**
     * Gets the authToken variable
     * @return authTokens list
     */
    public String getAuthToken() {
        return this.authToken;
    }

    /**
     * Gets the userName variable
     * @return userName
     */
    public String getUserName(){
        return this.userName;
    }

    /**
     * Gets the password variable
     * @return password
     */
    public String getPassword(){
        return this.password;
    }


}
