package client.user_actions;

import client.ClientConnectionManager;
import protocol.Request;
import protocol.UserActionType;

import java.util.HashMap;

public class CommentCommand extends BaseUserCommand implements IUserCommand{
    private String userId;
    private String initiativeId;
    private String comment;
    private String token;

    public CommentCommand(ClientConnectionManager connectionManager, String userId, String initiativeId, String comment, String token) {
        super(connectionManager);
        this.userId = userId;
        this.initiativeId = initiativeId;
        this.comment = comment;
        this.token = token;
    }

    @Override
    public void handleResponse() {

    }

    @Override
    public Request buildRequest() {

        HashMap<String, Object> details =
                new HashMap<>();

        details.put("userId", userId);

        details.put("initiativeId", initiativeId);

        details.put("commentText", "");

        Request request =
                new Request(
                        UserActionType.Comment,
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
