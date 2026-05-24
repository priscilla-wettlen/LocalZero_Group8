package client.user_actions;

import client.ClientConnectionManager;
import protocol.Initiative;
import protocol.Request;
import protocol.UserActionType;
import server.service.MessengerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowInboxCommand extends BaseUserCommand implements IUserCommand{

    private final String userId;
    private List<String> messages = new ArrayList<>();

    public ShowInboxCommand(ClientConnectionManager connectionManager, String userId){
        super(connectionManager);
        this.userId = userId;
    }


    @Override
    public void handleResponse(HashMap<String, Object> responseParam) {
        Object loadedMessages = responseParam.get("messages");

        if (loadedMessages instanceof List<?> ) {
            messages = new ArrayList<>();

            for (Object message : (List<?>)loadedMessages) {
                if (message != null) {
                    messages.add(message.toString());
                }
            }

        }
    }


    @Override

    public Request buildRequest() {
        HashMap<String, Object> details = new HashMap<>();
        details.put("userId", userId);
        Request request = new Request(UserActionType.ViewMessages, details);
        if (getConnectionManager() != null) {
            request.setAuthToken(getToken());
        }
        return request;
    }

    @Override
    public void execute() {
        if (getConnectionManager() != null) {
            Initiative response = sendRequest(buildRequest());
            if (response != null) {
                handleResponse(response.getResponseParam());
            }
            return;
        }
        messages = MessengerService.getMessengerServiceInstance()
                .getInboxMessages(userId);
    }



    public List<String> getMessages() {
        return messages;
    }
}


