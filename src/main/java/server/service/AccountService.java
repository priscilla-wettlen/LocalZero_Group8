package server.service;

import java.util.Map;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

import server.data_persistence.JsonSerializer;
import server.model.Neighborhood;
import server.model.User;


//This class has to be a Singleton like the rest of the Services!!
//Implementation of methods is missing still!!

public class AccountService implements IAccountService {

    private static AccountService accountServiceInstance;

    // Admin-kod
    private static final String ADMIN_CODE = "1234";

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

        
        if (role != null && role.equals(server.model.Role.CommunityOrganizer.name())) {
            if (adminCode == null || adminCode.isBlank() || !ADMIN_CODE.equals(adminCode)) {
                return null;
            }
        }

        
        if (role == null || !role.equals(server.model.Role.CommunityOrganizer.name())) {
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

        if (!BCrypt.checkpw(
                password,
                user.getPasswordHash())) {

            return null;
        }

        return user;
    }

    public User getUserByEmail(String email){
        return usersByEmail.get(email);
    }

    public User getUserById(String id) {

        for (User user : usersByEmail.values()) {

            if (user.getId().equals(id)) {
                return user;
            }
        }

        return null;
    }


}
