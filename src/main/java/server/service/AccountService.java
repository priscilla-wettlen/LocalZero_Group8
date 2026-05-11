package server.service;

import org.mindrot.jbcrypt.BCrypt;
import server.model.Role;
import server.model.User;
import server.security.JsonUserStore;

import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


//This class has to be a Singleton like the rest of the Services!!
//Implementation of methods is missing still!!

public class AccountService implements IAccountService {

    private static AccountService accountServiceInstance;
    private final Map<String, User> usersById = new ConcurrentHashMap<>();
    private final Map<String, User> usersByEmail = new ConcurrentHashMap<>();
    private final JsonUserStore userStore = new JsonUserStore(Paths.get("data/users.json"));

    private AccountService(){}

    public static AccountService getAccountServiceInstance() {
        if (accountServiceInstance == null) {
            accountServiceInstance = new AccountService();
        }
        return accountServiceInstance;
    }

    @Override
    public User register(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return null;
        }
        if (userStore.exists(username)) {
            return null;
        }
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        String userId = UUID.randomUUID().toString();

        JsonUserStore.StoredUser storedUser = new JsonUserStore.StoredUser();
        storedUser.id = userId;
        storedUser.email = username;
        storedUser.name = username;
        storedUser.passwordHash = passwordHash;
        storedUser.neighborhood = null;
        userStore.save(storedUser);

        User user = new User(username, null, username, passwordHash, userId, Role.Neighbor);
        usersById.put(user.getId(), user);
        usersByEmail.put(user.getEmail(), user);
        return user;
    }

    @Override
    public String login(String username, String password) {
        if (username == null || password == null) {
            return "";
        }
        User cached = usersByEmail.get(username);
        if (cached != null && cached.getPasswordHash() != null
                && BCrypt.checkpw(password, cached.getPasswordHash())) {
            return cached.getId();
        }

        return userStore.findByEmail(username)
                .filter(stored -> stored.passwordHash != null
                        && BCrypt.checkpw(password, stored.passwordHash))
                .map(stored -> {
                    User user = new User(
                            stored.name != null ? stored.name : stored.email,
                            null,
                            stored.email,
                            stored.passwordHash,
                            stored.id,
                            Role.Neighbor
                    );
                    usersById.put(user.getId(), user);
                    usersByEmail.put(user.getEmail(), user);
                    return user.getId();
                })
                .orElse("");
    }

    public User getUser(String userID){
        return usersById.get(userID);
    }

    public User getUserByEmail(String email){
        return usersByEmail.get(email);
    }

    public void addUser(User user){
        if (user == null || user.getEmail() == null) {
            return;
        }
        usersById.put(user.getId(), user);
        usersByEmail.put(user.getEmail(), user);
        JsonUserStore.StoredUser storedUser = new JsonUserStore.StoredUser();
        storedUser.id = user.getId();
        storedUser.email = user.getEmail();
        storedUser.name = user.getEmail();
        storedUser.passwordHash = user.getPasswordHash();
        storedUser.neighborhood = user.getNeighborhood() != null ? user.getNeighborhood().name() : null;
        userStore.save(storedUser);
    }

}
