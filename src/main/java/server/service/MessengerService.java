package main.java.server.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//Class is Singleton

public class MessengerService implements IMessengerService{
    private static MessengerService messengerServiceInstance;
    private Map<String, ArrayList<String>> messages = new ConcurrentHashMap<>();

    private MessengerService(){}

    public static MessengerService getMessengerServiceInstance(){
        if(messengerServiceInstance == null){
            messengerServiceInstance = new MessengerService();
        }
        return messengerServiceInstance;
    }


    @Override
    public void sendMessage(String senderUserID, String receiverUserID) {

    }

    @Override
    public ArrayList<String> getInboxMessages(String userID) {
        return messages.get(userID);
    }


}
