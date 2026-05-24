package server.service;

import server.model.InitiativeType;
import server.model.Neighborhood;
import server.model.User;
import server.model.Visibility;
import protocol.Request;
import protocol.Initiative;

import java.util.HashMap;
import java.util.List;

// The Coordinator gets an instance of every service.

public class Coordinator implements ICoordinator{
    private IInitiativeService initiativeService = InitiativeService.getInitiativeServiceInstance();
    private IAccountService accountService = AccountService.getAccountServiceInstance();
    private IMessengerService messengerService = MessengerService.getMessengerServiceInstance();
    private INotificationService notificationService = NotificationService.getNotificationServiceInstance();

    public Initiative processRequest(Request request){
        String requestActionType = request.getUserActionType().toString();
        HashMap<String, Object> paramsMap = request.getDetails();
        Initiative response = null;

        switch (requestActionType){
            case "CreateInitiative":
                response = extractParamsCreateInitiative(paramsMap);
                break;
            case "JoinInitiative":
                response = extractParamsJoinInitiative(paramsMap);
                break;
            case "PostUpdate":
                response = extractParamsPostUpdate(paramsMap);
                break;
            case "Comment":
                response = extractParamsComment(paramsMap);
                break;
            case "Like":
                response = extractParamsLike(paramsMap);
                break;
            case "LogEcoAction":
                response = extractLogEcoActionParams(paramsMap);
                break;
            case "ViewInitiatives":
                response = extractParamsViewForum(paramsMap);
                break;
            default:
                throw new IllegalArgumentException("Invalid request type!");
        }
        return response;
    }

/// These methods extract the parameters from the request according to the service
/// "contract" and call the respective service! I didn't want all this in the switch


    private Initiative extractParamsViewForum(HashMap<String, Object> params) {
        Neighborhood viewerNeighborhood = (Neighborhood) params.get("viewerNeighborhood");
        List<Initiative> initiatives = initiativeService.getForumInitiativesForViewer(viewerNeighborhood);
        Initiative response = new Initiative(true, "Forum initiatives loaded");
        response.putInitiativesList(initiatives);
        return response;
    }

    private Initiative extractParamsCreateInitiative(HashMap<String, Object> params){
        String creator = (String) params.get("username");
        String title = params.get("title").toString();
        String description = params.get("description").toString();
        String specificLocation = params.get("specificLocation") != null
                ? params.get("specificLocation").toString() : "";
        Neighborhood creatorNeighborhood = (Neighborhood) params.get("creatorNeighborhood");
        InitiativeType type = (InitiativeType) params.get("type");
        Visibility visibility = (Visibility) params.get("visibility");
        String duration = params.get("duration") != null ? params.get("duration").toString() : "";

        return createInitiative(creator, title, description, type, specificLocation, creatorNeighborhood, visibility, duration);
    }


    private Initiative extractLogEcoActionParams(HashMap<String, Object> paramsMap) {

        return null;
    }

    private Initiative extractParamsLike(
            HashMap<String, Object> paramsMap) {

        String initiativeId =
                (String) paramsMap.get("initiativeId");

        initiativeService.likeInitiative(
                initiativeId);

        return new Initiative(
                true,
                "Initiative liked successfully");
    }

    private Initiative extractParamsComment(
            HashMap<String, Object> paramsMap) {

        String initiativeId =
                (String) paramsMap.get("initiativeId");

        String name =
                (String) paramsMap.get("name");

        String commentText =
                (String) paramsMap.get("commentText");

        initiativeService.addComment(
                initiativeId,
                name,
                commentText
        );

        return new Initiative(
                true,
                "Comment added successfully");
    }

    private Initiative extractParamsPostUpdate(HashMap<String, Object> params) {
        return null;
    }

    private Initiative extractParamsJoinInitiative(
            HashMap<String, Object> params) {

        String userId =
                (String) params.get("userId");

        String initiativeId =
                (String) params.get("initiativeId");

        initiativeService.joinInitiative(
                userId,
                initiativeId);

        return new Initiative(
                true,
                "Joined initiative successfully");
    }



    // This methods should call the method in the corresponding service using the unique instances.
    @Override
    public User login(String username, String password) {
        return null;
    }

    @Override
    public User registerUser(String username, String password, Neighborhood neighborhood) {
        return null;
    }

    @Override
    public void sendMessage(String senderUserID, String recipientUserId, String message) {

    }

    @Override
    public Initiative createInitiative(String creator, String title, String description, InitiativeType type,
                                       String specificLocation, Neighborhood creatorNeighborhood,
                                       Visibility visibility, String duration) {
        String initiativeType = type != null ? type.name() : "General";
        return initiativeService.createInitiative(
                creator, title, description, initiativeType, specificLocation, creatorNeighborhood,
                visibility, duration, null);
    }

    @Override
    public void joinInitiative(String userID, String initiativeID) {

    }
    @Override
    public void postUpdate(String userID, String initiativeID, String title, String text) {

    }

    @Override
    public void logEcoAction(String userID, String actionType, String title, double carbonEquivalent) {

    }




}
