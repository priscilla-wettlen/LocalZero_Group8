package server.service;

import server.model.Initiative;
import server.model.Neighborhood;
import server.model.Visibility;
import shared.Response;

public interface IInitiativeService {

    public Response createInitiative(String creator, String title, String description, String initiativeType, Neighborhood location, Visibility visibility);
    public void joinInitiative(String initiativeID, String userID);

}
