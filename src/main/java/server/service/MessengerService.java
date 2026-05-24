package server.service;

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
    public void sendMessage(String senderName, String receiverUserID, String message) {
        if (receiverUserID == null || message == null || message.isBlank()) {
            return;
        }
        String displaySender;
        if(senderName == null || senderName.isBlank()){
            displaySender = "Sender unknown";
        }else{
            displaySender = senderName;
        }

        String formattedMessage = "From " + displaySender + ": " + message.trim();
        ArrayList<String> inboxMessages = messages.computeIfAbsent(receiverUserID, key -> new ArrayList<>());

        synchronized (inboxMessages) {
            inboxMessages.add(formattedMessage);
        }
    }


    @Override
    public ArrayList<String> getInboxMessages(String userID) {
        ArrayList<String> inboxMessages = messages.get(userID);

        if (inboxMessages == null) {
            return new ArrayList<>();
        }
        synchronized (inboxMessages) {
            return new ArrayList<>(inboxMessages);
        }

    }


}
