package main.java.client.user_actions;

import main.java.client.ClientConnectionManager;
import main.java.shared.Request;

public class PostUpdateAction extends BaseUserAction implements IUserAction{


    public PostUpdateAction(ClientConnectionManager connectionManager, String token) {
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
