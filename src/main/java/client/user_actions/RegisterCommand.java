package client.user_actions;

import client.ClientConnectionManager;
import shared.Request;

public class RegisterCommand extends BaseUserCommand implements IUserCommand {


    public RegisterCommand(ClientConnectionManager connectionManager, String token) {
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
