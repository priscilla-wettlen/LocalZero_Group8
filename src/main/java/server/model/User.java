package server.model;

import java.util.HashSet;

public class User {
    private String id;
    private String name;
    private Neighborhood neighborhood;
    private String email;
    private String passwordHash; ///Option to database??
    private HashSet<Role> roles = new HashSet<Role>();

    public User(String id, String name, Neighborhood neighborhood, String email, String passwordHash, Role roles) {
        this.id = id;
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
        return roles;}

    public String getPasswordHash() {
        return passwordHash;
    }
    public String getEmail() {
        return email;
    }
    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

}

