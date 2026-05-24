package client.user_actions;


import client.ClientConnectionManager;
import protocol.Request;
import protocol.Initiative;

public abstract class BaseUserCommand {
    private final ClientConnectionManager connectionManager;

    public BaseUserCommand(ClientConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    protected ClientConnectionManager getConnectionManager() {
        return connectionManager;
    }

//    public abstract void handleResponse();

    public abstract Request buildRequest();

    public final Initiative sendRequest(Request request) {
        if (connectionManager == null) {
            return null;
        }
        return connectionManager.sendRequest(request);
    }

    public final String getToken() {
        return connectionManager != null ? connectionManager.getToken() : null;
    }
}
