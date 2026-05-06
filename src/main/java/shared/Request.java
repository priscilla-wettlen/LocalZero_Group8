package main.java.shared;

import main.java.server.model.UserActionType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

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

    /// Getters  ...
}
