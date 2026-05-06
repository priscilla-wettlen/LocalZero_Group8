package main.java.client.user_actions;

import main.java.client.ClientConnectionManager;
import main.java.shared.Request;

public class CommentAction extends BaseUserAction implements IUserAction{


    public CommentAction(ClientConnectionManager connectionManager) {
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
