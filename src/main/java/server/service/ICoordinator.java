package server.service;

//This is the Mediator interface!!


import server.model.InitiativeType;
import server.model.Neighborhood;
import server.model.User;
import server.model.Visibility;
import protocol.Initiative;

public interface ICoordinator {
    public User login(String username, String password);
    public User registerUser(String username, String password, Neighborhood neighborhood);
    public void sendMessage(String senderUserID, String recipientUserId, String message);
    Initiative createInitiative(String creator, String title, String description, InitiativeType type,
                                String specificLocation, Neighborhood creatorNeighborhood,
                                Visibility visibility, String duration);
    public void joinInitiative(String userID, String initiativeID);
    public void postUpdate(String userID, String initiativeID, String title, String text);
    public void logEcoAction(String userID,String actionType, String title, double carbonEquivalent);


}
