package client.user_actions;

import client.view.ClientConnectionManager;
import shared.Request;

public class JoinInitiativeCommand extends BaseUserCommand implements IUserCommand {

    public JoinInitiativeCommand(ClientConnectionManager connectionManager, String token) {
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
