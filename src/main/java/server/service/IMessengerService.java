package main.java.server.service;

import java.util.ArrayList;

public interface IMessengerService {

    public void sendMessage(String senderUserID, String receiverUserID);

    public ArrayList<String> getInboxMessages(String userID);

}
