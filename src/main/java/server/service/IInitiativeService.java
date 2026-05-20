package server.service;

import server.model.Neighborhood;
import server.model.Visibility;
import shared.Initiative;

import java.net.URL;
import java.util.Map;

public interface IInitiativeService {

    public Initiative createInitiative(String creator, String title, String description, String initiativeType, Neighborhood location, Visibility visibility, String duration, URL image);
        Map<String, Initiative> getAllInitiatives();
    }
    /*
    public void joinInitiative(String initiativeID, String userID);

     */

