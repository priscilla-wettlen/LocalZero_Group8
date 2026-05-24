package server.service;

import server.model.Neighborhood;
import server.model.Visibility;
import protocol.Initiative;

import java.net.URL;
import java.util.Map;

public interface IInitiativeService {

    Initiative createInitiative(String creator, String title, String description, String initiativeType,
                                String specificLocation, Neighborhood creatorNeighborhood, Visibility visibility,
                                String duration, URL image);

    Map<String, Initiative> getAllInitiatives();

    java.util.List<Initiative> getForumInitiativesForViewer(Neighborhood viewerNeighborhood);

    void likeInitiative(String initiativeId);

    void addComment(String initiativeId,
                    String author,
                    String text);
    public void joinInitiative(String initiativeID, String userID);
}


