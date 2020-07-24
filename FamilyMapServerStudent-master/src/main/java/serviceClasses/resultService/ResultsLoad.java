package serviceClasses.resultService;

/**
 * Response Body for Load
 */
public class ResultsLoad {

    /**
     * Boolean to indicate if Response Body is a success
     */
    private Boolean success;

    /**
     * Message to describe if Response Body succeeded or failed
     */
    private String message;

    /**
     * Constructor for resultsLoad
     */
    public ResultsLoad(){
        this.success = false;
        this.message = "Error";
    }

    /**
     * Sets the success variable
     * @param success Indicates if the Response Body was a success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * Sets the message variable to describe the success or fail
     * @param message Description of success or fail
     */
    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * Gets the success variable
     * @return success
     */
    public Boolean getSuccess() {
        return this.success;
    }

    /**
     * Gets the message variable
     * @return message
     */
    public String getMessage() {
        return this.message;
    }
}