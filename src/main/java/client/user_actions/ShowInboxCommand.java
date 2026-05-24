package client.user_actions;

import client.ClientConnectionManager;
import protocol.Initiative;
import protocol.Request;
import protocol.UserActionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowInboxCommand extends BaseUserCommand implements IUserCommand {

    private final String userId;
    private final String senderName;
    private final Object neighborhood;
    private List<String> messages = new ArrayList<>();
    private List<String> neighbors = new ArrayList<>();
    private Map<String, String> neighborIds = new HashMap<>();

    public ShowInboxCommand(ClientConnectionManager connectionManager, String userId) {
        this(connectionManager, userId, "Neighbor", null);
    }

    public ShowInboxCommand(ClientConnectionManager connectionManager,
                            String userId,
                            String senderName,
                            Object neighborhood) {
        super(connectionManager);
        this.userId = userId;
        this.senderName = senderName;
        this.neighborhood = neighborhood;
    }

    @Override
    public void handleResponse(HashMap<String, Object> responseParam) {
        Object loadedMessages = responseParam.get("messages");
        if (loadedMessages instanceof List<?>) {
            messages = new ArrayList<>();
            for (Object message : (List<?>) loadedMessages) {
                if (message != null) {
                    messages.add(message.toString());
                }
            }
        }

        Object loadedNeighbors = responseParam.get("neighbors");
        if (loadedNeighbors instanceof List<?>) {
            neighbors = new ArrayList<>();
            for (Object neighbor : (List<?>) loadedNeighbors) {
                if (neighbor != null) {
                    neighbors.add(neighbor.toString());
                }
            }
        }

        Object loadedNeighborIds = responseParam.get("neighborIds");
        if (loadedNeighborIds instanceof Map<?, ?>) {
            neighborIds = new HashMap<>();
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) loadedNeighborIds).entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    neighborIds.put(entry.getKey().toString(), entry.getValue().toString());
                }
            }
        }
    }

    @Override
    public Request buildRequest() {
        HashMap<String, Object> details = new HashMap<>();
        details.put("userId", userId);
        if (neighborhood != null) {
            details.put("neighborhood", neighborhood);
        }

        Request request = new Request(UserActionType.ViewMessages, details);
        if (getConnectionManager() != null) {
            request.setAuthToken(getToken());
        }
        return request;
    }

    public Request buildSendMessageRequest(String recipient, String message) {
        HashMap<String, Object> details = new HashMap<>();
        details.put("senderName", senderName);
        details.put("recipientUserId", neighborIds.get(recipient));
        details.put("message", message);

        Request request = new Request(UserActionType.SendMessage, details);
        if (getConnectionManager() != null) {
            request.setAuthToken(getToken());
        }
        return request;
    }

    @Override
    public void execute() {
        if (getConnectionManager() == null) {
            return;
        }

        Initiative response = sendRequest(buildRequest());
        if (response != null) {
            handleResponse(response.getResponseParam());
        }
    }

    public boolean sendMessage(String recipient, String message) {
        if (getConnectionManager() == null || !neighborIds.containsKey(recipient)) {
            return false;
        }

        Initiative response = sendRequest(buildSendMessageRequest(recipient, message));
        return response != null && response.isSuccess();
    }

    public List<String> getMessages() {
        return messages;
    }

    public List<String> getNeighbors() {
        if (neighbors.isEmpty()) {
            execute();
        }
        return neighbors;
    }
}
