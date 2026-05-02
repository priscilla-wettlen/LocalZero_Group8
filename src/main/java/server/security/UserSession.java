package main.java.server.security;

import main.java.server.model.User;


public class UserSession {
    private User currentUser;
    private String token;

    public UserSession(User user, String token) {
        this.currentUser = user;
        this.token = token;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public String getToken() {
        return this.token;
    }
    public User getUser() {
        return this.currentUser;
    }


}
