package client.user_actions;

import client.ClientConnectionManager;
import protocol.Initiative;
import protocol.Request;
import protocol.UserActionType;
import server.model.InitiativeType;
import server.model.Neighborhood;
import server.model.Visibility;

import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

public class CreateInitiativeCommand
        extends BaseUserCommand
        implements IUserCommand {

    private String creator;

    private String title;

    private String description;

    private Visibility visibility;

    private InitiativeType initiativeType;

    private Neighborhood creatorNeighborhood;

    private String specificLocation;

    private String duration;

    private Date date;

    private URL image;

    public CreateInitiativeCommand(
            ClientConnectionManager connectionManager,
            String creator,
            String title,
            String description,
            Visibility visibility,
            Neighborhood creatorNeighborhood,
            InitiativeType initiativeType,
            String specificLocation,
            String duration,
            URL image) {

        super(connectionManager);

        this.creator = creator;

        this.title = title;

        this.description = description;

        this.visibility = visibility;

        this.creatorNeighborhood =
                creatorNeighborhood;

        this.initiativeType =
                initiativeType;

        this.specificLocation =
                specificLocation;

        this.duration =
                duration;

        this.image = image;

        this.date =
                Date.from(
                        Instant.now());
    }

    @Override
    public Request buildRequest() {

        HashMap<String, Object> details =
                new HashMap<>();

        details.put(
                "username",
                creator);

        details.put(
                "title",
                title);

        details.put(
                "description",
                description);

        details.put(
                "visibility",
                visibility);

        details.put(
                "type",
                initiativeType);

        details.put(
                "creatorNeighborhood",
                creatorNeighborhood);

        details.put(
                "specificLocation",
                specificLocation);

        details.put(
                "duration",
                duration);

        details.put(
                "image",
                image);

        details.put(
                "date",
                date);

        Request request =
                new Request(
                        UserActionType.CreateInitiative,
                        details);

        request.setAuthToken(
                super.getToken());

        return request;
    }

    @Override
    public void handleResponse(
            HashMap<String, Object> responseParam) {

        if (responseParam == null) {

            System.out.println(
                    "No response received");

            return;
        }

        System.out.println(
                "Initiative created successfully");
    }

    @Override
    public void execute() {

        Request request =
                buildRequest();

        Initiative response =
                super.sendRequest(request);

        if (response != null) {

            System.out.println(
                    response.getMessage());
        }
    }
}