package server.service;

import server.model.Neighborhood;
import server.model.Visibility;
import shared.Initiative;

import java.net.URL;
import java.util.Map;

public interface IInitiativeService {

    Initiative createInitiative(String creator, String title, String description, String initiativeType,
                                String specificLocation, Neighborhood creatorNeighborhood, Visibility visibility,
                                String duration, URL image);

    Map<String, Initiative> getAllInitiatives();

    java.util.List<Initiative> getForumInitiativesForViewer(Neighborhood viewerNeighborhood);
}
    /*
    public void joinInitiative(String initiativeID, String userID);

     */

