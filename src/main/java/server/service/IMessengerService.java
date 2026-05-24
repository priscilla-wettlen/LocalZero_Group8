package server.service;

import java.util.ArrayList;

public interface IMessengerService {

    public void sendMessage(String sender, String receiverUserID, String message);

    public ArrayList<String> getInboxMessages(String userID);

}
