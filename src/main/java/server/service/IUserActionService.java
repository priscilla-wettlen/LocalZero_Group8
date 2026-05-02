package main.java.server.service;

import main.java.server.model.Initiative;

public interface IUserActionService {

    public void postUpdate(String initiativeID, String userID); //here we need to think of images and text in the implementation!
    public void comment(String text, String userID);
    public void like(String userID, String initiativeID);

}

