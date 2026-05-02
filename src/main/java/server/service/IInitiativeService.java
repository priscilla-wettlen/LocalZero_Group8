package main.java.server.service;

import main.java.server.model.Initiative;
import main.java.server.model.Visibility;

public interface IInitiativeService {

    public Initiative createInitiative(String title, String description, String initiativeType, String visibility, String creatorUserID);
    public void joinInitiative(String initiativeID, String userID);

}
