package client.user_actions;

import client.ClientConnectionManager;
import protocol.Request;
import protocol.UserActionType;

import java.util.HashMap;

public class LikeCommand extends BaseUserCommand implements IUserCommand {
    private String userId;
    private String initiativeId;
    private String token;

    public LikeCommand(ClientConnectionManager connectionManager, String userId, String initiativeId, String token) {
        super(connectionManager);
        this.userId = userId;
        this.initiativeId = initiativeId;
        this.token = token;
    }

    @Override
    public void handleResponse() {
        System.out.println(
                "Like request completed");
    }

    @Override
    public Request buildRequest() {

        HashMap<String, Object> details =
                new HashMap<>();

        details.put("userId", userId);

        details.put("initiativeId", initiativeId);

        Request request =
                new Request(
                        UserActionType.Like,
                        details
                );

        request.setAuthToken(token);

        return request;
    }

    @Override
    public void execute() {
        Request request = buildRequest();
        getConnectionManager().sendRequest(request);
        handleResponse();
    }
}
