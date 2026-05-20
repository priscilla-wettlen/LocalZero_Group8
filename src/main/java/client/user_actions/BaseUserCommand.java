package client.user_actions;


import client.ClientConnectionManager;
import shared.Request;
import shared.Initiative;

public abstract class BaseUserCommand {
    private ClientConnectionManager connectionManager;



    public BaseUserCommand(ClientConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public abstract void handleResponse();

    public abstract Request buildRequest();

    public final Initiative sendRequest(Request request){
        Initiative response= connectionManager.sendRequest(request);
        return response;
    }

    public final String getToken() {
        return connectionManager.getToken();
    }
}
