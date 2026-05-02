package main.java.server.security;

import main.java.server.model.Neighborhood;
import main.java.server.model.User;
import main.java.server.service.ICoordinator;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/// This should be the class that checks for auth (if a user is logged in for
/// the session) for all requests/actions!!

public class AccessProxy implements ICoordinator {

    private ICoordinator coordinator;

    //I was thinking of saving the session tokens per user in this map
    private Map<String, UserSession> currentSessions = new ConcurrentHashMap<>();

    public AccessProxy(ICoordinator coordinator) {
        this.coordinator = coordinator;
    }

    /// Missing: Methods with logic to authenticate and set token to UserSession


    @Override
    public User login(String username, String password) {
        return coordinator.login(username, password);
    }

    @Override
    public User registerUser(String username, String password, Neighborhood neighborhood) {
        return null;
    }

    @Override
    public void sendMessage(String senderUserID, String recipientUserId, String message) {

    }

    @Override
    public void createInitiative(String userID, String title, String description, Neighborhood neighborhood) {

    }

    @Override
    public void joinInitiative(String userID, String initiativeID) {

    }

    @Override
    public void postUpdate(String userID, String initiativeID, String title, String text) {

    }

    @Override
    public void logEcoAction(String userID, String actionType, String title, double carbonEquivalent) {

    }


}
