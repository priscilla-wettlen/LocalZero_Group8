package server.model;

import java.util.HashSet;

public class User {
    private String id;
    private String name;
    private Neighborhood neighborhood;
    private String email;
    private String passwordHash; ///Option to database??
    private String adminCode;
    private String role;
    //private HashSet<Role> roles = new HashSet<Role>();

    public User(String id, String name, Neighborhood neighborhood, String email, String passwordHash, String adminCode, String role) {
        this.id = id;
        this.name = name;
        this.neighborhood = neighborhood;
        this.email = email;
        this.passwordHash = passwordHash;
        this.adminCode = adminCode;
        this.role = role;
        //this.roles = getRoles();
    }

//    public void addNewRole(Role role) {
//        roles.add(role);
//    }

    public String getId() {
        return id;
    }
//    public HashSet<Role> getRoles() {
//        return roles;}
    public String getRole(){
        return role;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public String getEmail() {
        return email;
    }
    public Neighborhood getNeighborhood() {
        return neighborhood;
    }
    public String getAdminCode() {
        return adminCode;
    }
}

