package server.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SustainabilityTracker implements ISustainabilityTracker{

    private Map<String,Double> userSavedCarbon = new ConcurrentHashMap<>();

    @Override
    public void logAction(String userID, String actionType, String title, double carbonEquivalent) {

    }

    @Override
    public double getCarbonSaved(String userID) {
        return userSavedCarbon.get(userID);
    }



}
