package server.service;

import server.model.InitiativeType;
import server.model.Neighborhood;
import server.model.User;
import server.model.Visibility;
import shared.Request;
import shared.Response;

import java.util.HashMap;

// The Coordinator gets an instance of every service.

public class Coordinator implements ICoordinator{
    private IInitiativeService initiativeService = InitiativeService.getInitiativeServiceInstance();
    private IAccountService accountService = AccountService.getAccountServiceInstance();
    private IMessengerService messengerService = MessengerService.getMessengerServiceInstance();
    private INotificationService notificationService = NotificationService.getNotificationServiceInstance();

    public Response processRequest(Request request){
        String requestActionType = request.getUserActionType().toString();
        HashMap<String, Object> paramsMap = request.getDetails();
        Response response = null;

        switch (requestActionType){
            case "createInitiative":
                response = extractParamsCreateInitiative(paramsMap);
                break;
            case "joinInitiative":
                response = extractParamsJoinInitiative(paramsMap);
                break;
            case "postUpdate":
                response = extractParamsPostUpdate(paramsMap);
                break;
            case "comment":
                response = extractParamsComment(paramsMap);
                break;
            case "like":
                response = extractParamsLike(paramsMap);
                break;
            case "logEcoAction":
                response = extractLogEcoActionParams(paramsMap);
                break;
            default:
                throw new IllegalArgumentException("Invalid request type!");
        }
        return response;
    }

/// These methods extract the parameters from the request according to the service
/// "contract" and call the respective service! I didn't want all this in the switch


    private Response extractParamsCreateInitiative(HashMap<String, Object> params){
        String creator = (String) params.get("username");
        String title = params.get("title").toString();
        String description = params.get("description").toString();
        Neighborhood location = (Neighborhood) params.get("neighborhood");
        InitiativeType type = (InitiativeType) params.get("type");
        Visibility visibility = (Visibility) params.get("visibility");

        return createInitiative(creator, title, description, type, location, visibility);
    }


    private Response extractLogEcoActionParams(HashMap<String, Object> paramsMap) {

        return null;
    }

    private Response extractParamsLike(HashMap<String, Object> paramsMap) {
        return null;
    }

    private Response extractParamsComment(HashMap<String, Object> paramsMap) {
        return null;
    }

    private Response extractParamsPostUpdate(HashMap<String, Object> params) {
        return null;
    }

    private Response extractParamsJoinInitiative(HashMap<String, Object> params) {
        return null;
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
    public Response createInitiative(String creator, String title, String description, InitiativeType type, Neighborhood location, Visibility visibility) {


        return null;

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
