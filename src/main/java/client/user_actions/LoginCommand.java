package client.user_actions;

import client.ClientConnectionManager;
import protocol.Request;

public class LoginCommand extends BaseUserCommand implements IUserCommand {


    public LoginCommand(ClientConnectionManager connectionManager, String token) {
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
