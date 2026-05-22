package client.user_actions;

import client.ClientConnectionManager;
import server.model.Neighborhood;
import server.service.InitiativeService;
import shared.Initiative;
import shared.Request;
import shared.UserActionType;

import java.util.HashMap;
import java.util.List;

/**
 * Command that loads initiatives visible on the forum for the logged-in viewer.
 * Uses the mediator (Coordinator) when a connection is available; otherwise falls back to local service.
 */
public class ViewForumCommand extends BaseUserCommand implements IUserCommand {

    private final Neighborhood viewerNeighborhood;
    private List<Initiative> loadedInitiatives = List.of();

    public ViewForumCommand(ClientConnectionManager connectionManager, Neighborhood viewerNeighborhood) {
        super(connectionManager);
        this.viewerNeighborhood = viewerNeighborhood;
    }

    public ViewForumCommand(Neighborhood viewerNeighborhood) {
        super(null);
        this.viewerNeighborhood = viewerNeighborhood;
    }

    @Override
    public Request buildRequest() {
        HashMap<String, Object> details = new HashMap<>();
        details.put("viewerNeighborhood", viewerNeighborhood);
        Request request = new Request(UserActionType.ViewInitiatives, details);
        if (getConnectionManager() != null) {
            request.setAuthToken(getToken());
        }
        return request;
    }

    @Override
    public void handleResponse() {
        // Populated in execute() after sendRequest or local load.
    }

    @Override
    public void execute() {
        if (getConnectionManager() != null) {
            Initiative response = sendRequest(buildRequest());
            if (response != null && response.isSuccess()) {
                loadedInitiatives = response.getInitiativesList();
            }
        } else {
            loadedInitiatives = InitiativeService.getInitiativeServiceInstance()
                    .getForumInitiativesForViewer(viewerNeighborhood);
        }
    }

    public List<Initiative> getLoadedInitiatives() {
        return loadedInitiatives;
    }
}
