package main.java.server.service;

//This is the Mediator interface!!


import main.java.server.model.Neighborhood;
import main.java.server.model.User;

public interface ICoordinator {
    public User login(String username, String password);
    public User registerUser(String username, String password, Neighborhood neighborhood);
    public void sendMessage(String senderUserID, String recipientUserId, String message);
    public void createInitiative(String userID, String title, String description, Neighborhood neighborhood);
    public void joinInitiative(String userID, String initiativeID);
    public void postUpdate(String userID, String initiativeID, String title, String text); //I think the assignment calls for more than text...
    public void logEcoAction(String userID,String actionType, String title, double carbonEquivalent);


}
