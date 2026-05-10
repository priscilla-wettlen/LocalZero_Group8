package server.model;

public class Message {
private String messageBody;
private User sender;


public String getMessageBody() {
    return messageBody;
}

public void setMessageBody(String messageBody) {
    this.messageBody= messageBody;
}

public  User getSender() {
    return sender;
}
public void setSender(User sender) {
    this.sender = sender;
}


}
