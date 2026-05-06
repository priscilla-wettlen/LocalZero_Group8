package main.java.client.user_actions;

import main.java.client.ClientConnectionManager;
import main.java.shared.Request;

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
