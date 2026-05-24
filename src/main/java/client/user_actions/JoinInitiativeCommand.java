package client.user_actions;

import client.ClientConnectionManager;
import protocol.Request;

import java.util.HashMap;

public class JoinInitiativeCommand extends BaseUserCommand implements IUserCommand {

    public JoinInitiativeCommand(ClientConnectionManager connectionManager, String token) {
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
