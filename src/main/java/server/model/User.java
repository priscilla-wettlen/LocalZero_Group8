package main.java.server.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String ID;
    private Neighborhood neighborhood;
    private String passwordHash; ///Option to database??
    private HashSet<Role> roles = new HashSet<Role>();


    public User(String ID, Role role, String passwordHash,  Neighborhood neighborhood) {}

    public void addNewRole(Role role) {
        roles.add(role);
    }

    public String getID() {
        return ID;
    }
    public HashSet<Role> getRoles() {
        return roles;}

}

