package main.java.server.model;

import java.util.HashSet;

public class User {
    private String name;
    private Neighborhood neighborhood;
    private String email;
    private String passwordHash; ///Option to database??
    private String id;
    private HashSet<Role> roles = new HashSet<Role>();


    public User(String name, Neighborhood neighborhood, String email, String passwordHash, String id, Role role) {
        this.name = name;
        this.neighborhood = neighborhood;
        this.email = email;
        this.passwordHash = passwordHash;
        this.id = id;
        this.roles = getRoles();
    }

    public void addNewRole(Role role) {
        roles.add(role);
    }

    public String getId() {
        return id;
    }
    public HashSet<Role> getRoles() {
        return roles;
    }
}

