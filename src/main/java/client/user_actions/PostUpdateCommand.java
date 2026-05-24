package client.user_actions;

import client.ClientConnectionManager;
import protocol.Request;

import java.util.HashMap;

public class PostUpdateCommand extends BaseUserCommand implements IUserCommand {

    public PostUpdateCommand(ClientConnectionManager connectionManager, String token) {
        super(connectionManager);
    }

    @Override
    public void handleResponse(HashMap<String, Object> responseParam) {
    }

    @Override
    public Request buildRequest() {
        return null;
    }

    @Override
    public void execute() {
    }
}
