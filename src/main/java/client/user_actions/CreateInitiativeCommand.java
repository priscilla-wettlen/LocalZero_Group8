package client.user_actions;

import client.ClientConnectionManager;
import server.model.InitiativeType;
import server.model.Neighborhood;
import protocol.UserActionType;
import server.model.Visibility;
import protocol.Request;
import protocol.Initiative;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;


public class CreateInitiativeCommand extends BaseUserCommand implements IUserCommand{
    private String title;
    private String description;
    private Visibility visibility;
    private InitiativeType initiativeType;
    private Neighborhood neighborhood;
    private Date date;

    public CreateInitiativeCommand(ClientConnectionManager connectionManager, String title, String description, Visibility visibility,
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
        HashMap<String, Object> details = new HashMap<>();
        details.put("title",title);
        details.put("description",description);
        details.put("visibility", visibility);
        details.put("initiativeType",initiativeType);
        details.put("neighborhood",neighborhood);
        details.put("date", date);
        Request request = new Request(UserActionType.CreateInitiative, details);
        request.setAuthToken(super.getToken()); ////this is to get the token from the clientConnectionManager to attach it to the request
        return request;
    }
    @Override
    public void handleResponse(HashMap<String, Object> responsParam) {
        ////Do whatever with response, it should maybe always give success/fail as return???
    }

    @Override
    public void execute(){
        Request request = buildRequest();
        Initiative response = super.sendRequest(request);
        handleResponse();

    }
}
