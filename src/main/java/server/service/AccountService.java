package server.service;

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
    public User register(String name,
                         Neighborhood neighborhood,
                         String email,
                         String password,
                         String adminCode,
                         String role) {

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
    public String login(String email,
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
            return "";
        }

        if (!BCrypt.checkpw(password,
                user.getPasswordHash())) {

            return "";
        }

        return user.getId();
    }

    public User getUserByEmail(String email) {
        return usersByEmail.get(email);
    }

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
