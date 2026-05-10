package server.service;

import server.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


//This class has to be a Singleton like the rest of the Services!!
//Implementation of methods is missing still!!

public class AccountService implements IAccountService {

    private static AccountService accountServiceInstance;
    private Map<String, User> users = new ConcurrentHashMap<>();

    private AccountService(){}

    public static AccountService getAccountServiceInstance() {
        if (accountServiceInstance == null) {
            accountServiceInstance = new AccountService();
        }
        return accountServiceInstance;
    }

    @Override
    public User register(String username, String password) {
        return null;
    }

    @Override
    public String login(String username, String password) {

        return "userID";
    }

    public User getUser(String userID){
        return users.get(userID);
    }

    public User getUserByEmail(String email){
        return users.get(email);
    }

    public void addUser(User user){users.put(user.getEmail(), user);}

}
