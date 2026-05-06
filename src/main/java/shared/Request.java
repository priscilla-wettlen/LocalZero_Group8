package main.java.shared;

import main.java.server.model.User;
import main.java.server.model.UserActionType;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;

public class Request implements Serializable {
    private UserActionType userActionType;
    private HashSet details;
    private String authToken; //for login and register it could be null?? MMhhmm...


    public Request(UserActionType userActionType, HashSet details) {
        this.userActionType = userActionType;
        this.details = details;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /// Getters  ...
}
