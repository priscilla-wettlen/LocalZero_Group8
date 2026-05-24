package client.user_actions;

import client.ClientConnectionManager;
import protocol.Request;
import protocol.UserActionType;

import java.util.HashMap;

public class UpdateInitiativeCommand extends BaseUserCommand implements IUserCommand {
    private final String initiativeId;
    private final String title;
    private final String description;
    private final String actorEmail;

    public UpdateInitiativeCommand(ClientConnectionManager cm, String initiativeId, String title,
                                   String description, String actorEmail) {
        super(cm);
        this.initiativeId = initiativeId;
        this.title = title;
        this.description = description;
        this.actorEmail = actorEmail;
    }

    @Override
    public Request buildRequest() {
        HashMap<String, Object> details = new HashMap<>();
        details.put("initiativeId", initiativeId);
        details.put("title", title);
        details.put("description", description);
        details.put("actorEmail", actorEmail);
        Request request = new Request(UserActionType.UpdateInitiative, details);
        request.setAuthToken(getToken());
        return request;
    }

    @Override
    public void handleResponse(HashMap<String, Object> responseParam) {}

    @Override
    public void execute() {
        sendRequest(buildRequest());
    }
}