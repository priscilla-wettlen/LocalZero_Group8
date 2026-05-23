package client.user_actions;


import client.view.ClientConnectionManager;
import shared.Request;
import shared.Initiative;

public abstract class BaseUserCommand {
    private final ClientConnectionManager connectionManager;

    public BaseUserCommand(ClientConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    protected ClientConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public abstract void handleResponse();

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
