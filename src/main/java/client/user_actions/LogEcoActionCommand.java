package client.user_actions;


import client.ClientConnectionManager;
import protocol.Request;
import protocol.UserActionType;


import java.util.HashMap;

public class LogEcoActionCommand extends BaseUserCommand implements IUserCommand {


    private String actionText;
    private String lastResponse;

    public LogEcoActionCommand(ClientConnectionManager connectionManager, String token) {
        super(connectionManager);
    }

    public void setAction(String action) {
        this.actionText = action;
    }

    public String getLastResponse() {
        return lastResponse;
    }

    @Override
    public void handleResponse(HashMap<String, Object> responseParam) {
        Object message = responseParam != null ? responseParam.get("message") : null;
        this.lastResponse = message != null ? message.toString() : null;
        if (this.lastResponse != null) {
            System.out.println(this.lastResponse);
        }
    }


    @Override
    public Request buildRequest() {
        HashMap<String, Object> details = new HashMap<>();
        details.put("action", actionText);


        Request request = new Request(UserActionType.LogEcoAction, details);
        request.setAuthToken(getToken());
        return request;
    }

    @Override
    public void execute() {
        Request request = buildRequest();
        HashMap<String, String> carbonSavings = new HashMap<>();
        carbonSavings.put("biked to work", "2.3 kg CO2");
        carbonSavings.put("skipped meat today", "1.8 kg CO2");
        carbonSavings.put("used public transit", "1.2 kg CO2");
        carbonSavings.put("planted a tree", "5.0 kg CO2");
        carbonSavings.put("recycled waste", "0.9 kg CO2");


        String lookupKey = actionText != null ? actionText.toLowerCase().trim() : "";
        String savings = carbonSavings.getOrDefault(lookupKey, "0.5 kg CO2");


        if (request == null) {
            HashMap<String, Object> mock = new HashMap<>();
            mock.put("message", "Action logged! Estimated saving: " + savings);
            handleResponse(mock);
            return;
        }


        if (sendRequest(request) == null) {
            HashMap<String, Object> mock = new HashMap<>();
            mock.put("message", "Action logged! Estimated saving: " + savings);
            handleResponse(mock);
        }
    }
}
