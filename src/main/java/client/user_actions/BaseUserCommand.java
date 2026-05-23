package client.user_actions;


import client.ClientConnectionManager;
import shared.Request;
import shared.Response;

import java.util.HashMap;

public abstract class BaseUserCommand {
    private final ClientConnectionManager connectionManager;

    public BaseUserCommand(ClientConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    protected ClientConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public abstract void handleResponse(HashMap<String, Object> responsParam);

    public abstract Request buildRequest();


    public final HashMap<String, Object> sendRequest(Request request) {
        if (connectionManager == null) {
            return null;
        }
        return connectionManager.sendRequest(request);
    }

    public final String getToken() {
        return connectionManager != null ? connectionManager.getToken() : null;
    }
}
