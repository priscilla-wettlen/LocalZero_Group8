package main.java.client.user_actions;

import main.java.client.ClientConnectionManager;
import main.java.server.model.InitiativeType;
import main.java.server.model.Neighborhood;
import main.java.server.model.UserActionType;
import main.java.server.model.Visibility;
import main.java.shared.Request;
import main.java.shared.Response;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;


public class CreateInitiativeAction extends BaseUserAction implements IUserAction {
    private String title;
    private String description;
    private Visibility visibility;
    private InitiativeType initiativeType;
    private Neighborhood neighborhood;
    private Date date;

    public CreateInitiativeAction(ClientConnectionManager connectionManager, String title, String description, Visibility visibility,
                                  Neighborhood neighborhood, InitiativeType initiativeType) {
        super(connectionManager);

        this.title = title;
        this.description = description;
        this.visibility = visibility;
        this.neighborhood = neighborhood;
        this.date = Date.from(Instant.now());
        this.initiativeType = initiativeType;

    }


    @Override
    public Request buildRequest() {
        HashSet details = new HashSet();
        details.add(title);
        details.add(description);
        details.add(visibility);
        details.add(initiativeType);
        details.add(neighborhood);
        details.add(date);
        Request request = new Request(UserActionType.CreateInitiative, details);
        request.setAuthToken(super.getToken()); ////this is to get the token from the clientConnectionManager to attach it to the request
        return request;
    }
    @Override
    public void handleResponse() {
        ////Do whatever with response, it should maybe always give success/fail as return???
    }

    @Override
    public void execute(){
        Request request = buildRequest();
        Response response = super.sendRequest(request);
        handleResponse();

    }
}
