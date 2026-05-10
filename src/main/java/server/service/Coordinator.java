package server.service;

import server.model.Neighborhood;
import server.model.User;

// The Coordinator gets an instance of every service.

public class Coordinator implements ICoordinator{
    private IInitiativeService initiativeService = InitiativeService.getInitiativeServiceInstance();
    private IAccountService accountService = AccountService.getAccountServiceInstance();
    private IMessengerService messengerService = MessengerService.getMessengerServiceInstance();
    private INotificationService notificationService = NotificationService.getNotificationServiceInstance();

    // This methods should call the method in the corresponding service using the unique instances.
    @Override
    public User login(String username, String password) {
        return null;
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
