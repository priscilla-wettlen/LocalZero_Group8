package main.java.client.user_actions;


import main.java.client.ClientConnectionManager;
import main.java.shared.Request;
import main.java.shared.Response;

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
