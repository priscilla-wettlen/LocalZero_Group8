package main.java.server.model;

import java.sql.Date;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Inbox {
    Map<String, Message> messages = new ConcurrentHashMap<>();

    public void addMessage(Message message){
        String date = Date.from(Instant.now()).toString();
        messages.put(date,message);
    }

}
