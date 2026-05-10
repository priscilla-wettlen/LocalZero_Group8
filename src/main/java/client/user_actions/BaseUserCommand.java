package client.user_actions;


import client.ClientConnectionManager;
import shared.Request;
import shared.Response;

public abstract class BaseUserCommand {
    private ClientConnectionManager connectionManager;



    public BaseUserCommand(ClientConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public abstract void handleResponse();

    public abstract Request buildRequest();

    public final Response sendRequest(Request request){
        Response response= connectionManager.sendRequest(request);
        return response;
    }

    public final String getToken() {
        return connectionManager.getToken();
    }
}
