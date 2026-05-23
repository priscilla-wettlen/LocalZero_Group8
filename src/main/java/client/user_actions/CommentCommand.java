package client.user_actions;

import client.ClientConnectionManager;
import shared.Request;

import java.util.HashMap;

public class CommentCommand extends BaseUserCommand implements IUserCommand{


    public CommentCommand(ClientConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    public void handleResponse(HashMap<String, Object> responsParam) {

    }

    @Override
    public Request buildRequest() {

        return null;
    }

    @Override
    public void execute() {

    }
}
