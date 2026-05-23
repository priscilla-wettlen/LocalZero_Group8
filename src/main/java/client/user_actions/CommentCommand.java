package client.user_actions;

import client.ClientConnectionManager;
import protocol.Request;
import protocol.UserActionType;

import java.util.HashMap;

public class CommentCommand extends BaseUserCommand implements IUserCommand{
    private String userName;
    private String initiativeId;
    private String comment;

    public CommentCommand(ClientConnectionManager connectionManager, String userName, String initiativeId, String comment) {
        super(connectionManager);
        this.userName = userName;
        this.initiativeId = initiativeId;
        this.comment = comment;
    }

    @Override
    public void handleResponse() {

    }

    @Override
    public Request buildRequest() {

        HashMap<String, Object> details =
                new HashMap<>();

        details.put("name", userName);

        details.put("initiativeId", initiativeId);

        details.put("commentText", comment);

        Request request =
                new Request(
                        UserActionType.Comment,
                        details
                );

        request.setAuthToken(getToken());

        return request;
    }

    @Override
    public void execute() {
        Request request = buildRequest();
        getConnectionManager().sendRequest(request);
        handleResponse();
    }
}
