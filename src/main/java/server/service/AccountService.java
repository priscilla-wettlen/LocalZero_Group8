package server.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;
import server.data_persistence.JsonSerializer;
import server.model.Neighborhood;
import server.model.Role;
import server.model.User;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class AccountService implements IAccountService {

    private static AccountService accountServiceInstance;

    // Admin-kod
    private static final String ADMIN_CODE = "1234";

    private final JsonSerializer<User> serializer =
            new JsonSerializer<>(User.class);
    private static final String FILE_PATH =
            "shared/users.json";
    private final Map<String, User> usersByEmail = serializer.loadSavedData(FILE_PATH);

    private AccountService() {
    }

    public static AccountService getAccountServiceInstance() {
        if (accountServiceInstance == null) {
            accountServiceInstance = new AccountService();
        }
        return accountServiceInstance;
    }

    @Override
    public User register(String name, Neighborhood neighborhood, String email, String password, String adminCode, String role) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.Resident);

        if (role != null && role.equals(Role.CommunityOrganizer.name())) {
            if (adminCode == null || adminCode.isBlank() || !ADMIN_CODE.equals(adminCode)) {
                return null;
            }
            roles.add(Role.CommunityOrganizer);
        } else {
            adminCode = null;
        }

        Map<String, User> users =
                serializer.loadSavedData(FILE_PATH);

        if (users.containsKey(email)) {
            return null;
        }

        String passwordHash =
                BCrypt.hashpw(password, BCrypt.gensalt());
        String userId =
                UUID.randomUUID().toString();
        User user = new User(
                userId,
                name,
                neighborhood,
                email,
                passwordHash,
                adminCode,
                roles
        );

        users.put(email, user);
        serializer.save(FILE_PATH, users);
        usersByEmail.put(email, user);
        return user;
    }

    @Override
    public User login(String email,
                      String password) {

        User user = usersByEmail.get(email);

        if (user == null) {

            Map<String, User> users =
                    serializer.loadSavedData(FILE_PATH);

            user = users.get(email);

            if (user != null) {
                usersByEmail.put(email, user);
            }
        }

        if (user == null) {
            return null;
        }

        if (!BCrypt.checkpw(password,
                user.getPasswordHash())) {

            return null;
        }

        return user;
    }

    public User getUserByEmail(String email) {
        return usersByEmail.get(email);
    }

    @Override
    public List<User> getUsersByNeighborhood(Neighborhood neighborhood, String excludedUserId) {
        Map<String, User> users = serializer.loadSavedData(FILE_PATH);
        usersByEmail.putAll(users);

        return users.values().stream()
                .filter(user -> user.getNeighborhood() == neighborhood)
                .filter(user -> excludedUserId == null || !excludedUserId.equals(user.getId()))
                .sorted(Comparator.comparing(User::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }





//    public void addUser(User user){
//        if (user == null || user.getEmail() == null) {
//            return;
//        }
//        usersById.put(user.getId(), user);
//        usersByEmail.put(user.getEmail(), user);
//        JsonUserStore.StoredUser storedUser = new JsonUserStore.StoredUser();
//        storedUser.id = user.getId();
//        storedUser.email = user.getEmail();
//        storedUser.name = user.getEmail();
//        storedUser.passwordHash = user.getPasswordHash();
//        storedUser.neighborhood = user.getNeighborhood() != null ? user.getNeighborhood().name() : null;
//        userStore.save(storedUser);
//    }
    public boolean updateUserRoles(String email, boolean wantsOrganizer, String adminCode) {
        if (email == null || email.isBlank()) {
            return false;
        }

        User user = usersByEmail.get(email);
        if (user == null) {
            Map<String, User> users = serializer.loadSavedData(FILE_PATH);
            user = users.get(email);
            if (user != null) {
                usersByEmail.put(email, user);
            }
        }

        if (user == null) {
            return false;
        }

        if (wantsOrganizer) {
            if (adminCode == null || adminCode.isBlank() || !ADMIN_CODE.equals(adminCode)) {
                return false;
            }
        }

        Set<Role> roles = new HashSet<>();
        roles.add(Role.Resident);
        if (wantsOrganizer) {
            roles.add(Role.CommunityOrganizer);
            user.setAdminCode(adminCode);
        } else {
            user.setAdminCode(null);
        }
        user.setRoles(roles);

        Map<String, User> users = serializer.loadSavedData(FILE_PATH);
        users.put(email, user);
        serializer.save(FILE_PATH, users);
        usersByEmail.put(email, user);

        return true;
    }
}
