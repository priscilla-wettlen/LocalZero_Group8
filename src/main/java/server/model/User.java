package server.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class User {
    private String id;
    private String name;
    private Neighborhood neighborhood;
    private String email;
    private String passwordHash; ///Option to databasee??
    private String adminCode;
    private String role; // old one?
    private Set<Role> roles = new HashSet<>();

    public User(String id, String name, Neighborhood neighborhood, String email, String passwordHash, String adminCode,
                Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.neighborhood = neighborhood;
        this.email = email;
        this.passwordHash = passwordHash;
        this.adminCode = adminCode;
        this.roles = roles != null ? new HashSet<>(roles) : new HashSet<>();
        ensureRolesInitialized();
    }

    /**
     * Backward-compatible constructor for older code paths.
     */
    public User(String id, String name, Neighborhood neighborhood, String email, String passwordHash, String adminCode,
                String role) {
        this.id = id;
        this.name = name;
        this.neighborhood = neighborhood;
        this.email = email;
        this.passwordHash = passwordHash;
        this.adminCode = adminCode;
        this.role = role;
        ensureRolesInitialized();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }

    public Set<Role> getRoles() {
        ensureRolesInitialized();
        return new HashSet<>(roles);
    }

    public boolean hasRole(Role role) {
        ensureRolesInitialized();
        return roles.contains(role);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles != null ? new HashSet<>(roles) : new HashSet<>();
        ensureRolesInitialized();
    }

    public void addRole(Role role) {
        ensureRolesInitialized();
        roles.add(role);
        roles.add(Role.Resident); // enforce baseline
    }

    public void removeRole(Role role) {
        ensureRolesInitialized();
        if (role != Role.Resident) {
            roles.remove(role);
        }
        roles.add(Role.Resident);
    }

    /**
     * Display-friendly role string used in UI.
     */
    public String getRole() {
        ensureRolesInitialized();
        return roles.stream()
                .map(Role::name)
                .sorted()
                .collect(Collectors.joining(", "));
    }

    /**
     * For old accounts.
     */
    private void ensureRolesInitialized() {
        if (roles == null) {
            roles = new HashSet<>();
        }
        if (roles.isEmpty() && role != null && !role.isBlank()) {
            try {
                roles.add(Role.valueOf(role));
            } catch (IllegalArgumentException ignored) {
            }
        }
        roles.add(Role.Resident);
    }
}
