package shared;

import java.io.Serializable;
import java.util.HashMap;

public class Request implements Serializable {
    private UserActionType userActionType;
    private HashMap<String, Object> details;
    private String authToken; //for login and register it could be null?? MMhhmm...


    public Request(UserActionType userActionType, HashMap<String, Object> details) {
        this.userActionType = userActionType;
        this.details = details;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public HashMap<String, Object> getDetails() {
        return details;
    }


    public void setDetails(HashMap<String, Object> details) {
        this.details = details;
    }
    public void setUserActionType(UserActionType userActionType) {
        this.userActionType = userActionType;
    }

    public  UserActionType getUserActionType() {
        return userActionType;
    }
}
