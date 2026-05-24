package client.user_actions;

import java.util.HashMap;

import client.ClientConnectionManager;
import protocol.Request;
import protocol.UserActionType;

public class LikeCommand
        extends BaseUserCommand
        implements IUserCommand {

    private String userId;

    private String initiativeId;

    public LikeCommand(
            ClientConnectionManager connectionManager,
            String userId,
            String initiativeId) {

        super(connectionManager);

        this.userId = userId;

        this.initiativeId = initiativeId;
    }

    @Override
    public void handleResponse(HashMap<String, Object> responseParam) {

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

        request.setAuthToken(getToken());

        return request;
    }

    @Override
    public void execute() {

        Request request = buildRequest();

        getConnectionManager().sendRequest(request);

        //handleResponse();
    }
}