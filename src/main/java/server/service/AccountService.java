package server.service;

import org.mindrot.jbcrypt.BCrypt;
import server.data_persistence.JsonSerializer;
import server.model.Neighborhood;
import server.model.Role;
import server.model.User;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


//This class has to be a Singleton like the rest of the Services!!
//Implementation of methods is missing still!!

public class AccountService implements IAccountService {

    private static AccountService accountServiceInstance;
//    private final Map<String, User> usersById = new ConcurrentHashMap<>();
    private final JsonSerializer<User> serializer =
        new JsonSerializer<>(User.class);
    private static final String FILE_PATH =
            "shared/users.json";
    private final Map<String, User> usersByEmail = serializer.loadSavedData(FILE_PATH);

    private AccountService(){}

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
                         String role){
                         //HashSet<Role> roles) {

        Map<String, User> users =
                serializer.loadSavedData(FILE_PATH);

        if (users.containsKey(email)) {
            return null;
        }

        String passwordHash =
                BCrypt.hashpw(password, BCrypt.gensalt());



        String userId =
                /*
                * Creates the random IDs
                * */
                UUID.randomUUID().toString();

        User user = new User(
                userId,
                name,
                neighborhood,
                email,
                passwordHash,
                adminCode,
                role
                //Role.Neighbor
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

//    public User getUser(String userID){
//        return usersById.get(userID);
//    }

    public User getUserByEmail(String email){
        return usersByEmail.get(email);
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

}
