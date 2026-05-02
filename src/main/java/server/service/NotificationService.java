package main.java.server.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//Singleton class also
//Resources like in other classes are concurrent collections/maps
public class NotificationService implements INotificationService {
    private static NotificationService notificationServiceInstance;
    private Map<String, ArrayList<String>> notifications  = new ConcurrentHashMap<>();

    private NotificationService(){}

    public static NotificationService getNotificationServiceInstance(){
        if(notificationServiceInstance == null){
            notificationServiceInstance = new NotificationService();
        }
        return notificationServiceInstance;
    }

    @Override
    public void notify(String userID, String Message) {

    }

    public Map<String, ArrayList<String>> getNotifications(){
        return notifications;
    }
}
