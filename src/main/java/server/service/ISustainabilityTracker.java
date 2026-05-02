package main.java.server.service;

public interface ISustainabilityTracker {
    public void logAction(String userID, String actionType, String title,double carbonEquivalent);

    public double getCarbonSaved(String userID);
}
