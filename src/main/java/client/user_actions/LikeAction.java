package main.java.client.user_actions;

import main.java.client.ClientConnectionManager;
import main.java.shared.Request;

public class LikeAction extends BaseUserAction implements IUserAction{


    public LikeAction(ClientConnectionManager connectionManager, String token) {
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
