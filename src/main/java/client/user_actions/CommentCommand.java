package client.user_actions;

import client.view.ClientConnectionManager;
import shared.Request;

public class CommentCommand extends BaseUserCommand implements IUserCommand{


    public CommentCommand(ClientConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    public void handleResponse() {

    }

    @Override
    public Request buildRequest() {

        return null;
    }

    @Override
    public void execute() {

    }
}
