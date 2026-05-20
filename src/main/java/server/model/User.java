package server.model;

public class User {

    private String id;

    private String name;

    private Neighborhood neighborhood;

    private String email;

    private String passwordHash;

    private Role role;

    public User(String id,
                String name,
                Neighborhood neighborhood,
                String email,
                String passwordHash,
                Role role) {

        this.id = id;
        this.name = name;
        this.neighborhood = neighborhood;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }
}