package server.service;

import server.model.Initiative;

public interface IInitiativeService {

    public Initiative createInitiative(String title, String description, String initiativeType, String visibility, String creatorUserID);
    public void joinInitiative(String initiativeID, String userID);

}
