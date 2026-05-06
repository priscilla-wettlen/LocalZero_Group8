package main.java.client.user_actions;

import main.java.client.ClientConnectionManager;
import main.java.shared.Request;

public class PostUpdateCommand extends BaseUserCommand implements IUserCommand {


    public PostUpdateCommand(ClientConnectionManager connectionManager, String token) {
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
