package server.service;

import server.model.Initiative;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//Singleton class, like all service classes!
public class InitiativeService implements IInitiativeService{
    private static InitiativeService initiativeServiceInstance;
    private Map<String, Initiative> initiatives =  new ConcurrentHashMap<>();


    private InitiativeService(){}

    public static InitiativeService getInitiativeServiceInstance(){
        if(initiativeServiceInstance == null){
            initiativeServiceInstance = new InitiativeService();
        }
        return initiativeServiceInstance;
    }

    @Override
    public Initiative createInitiative(String title, String description, String initiativeType, String visibility, String creatorUserID) {
        return null;
    }

    @Override
    public void joinInitiative(String initiativeID, String userID) {

    }


}
