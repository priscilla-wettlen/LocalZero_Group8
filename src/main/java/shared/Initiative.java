package shared;

import server.model.Neighborhood;
import server.model.Visibility;

import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class Initiative implements Serializable {
    private String id;
    private String title;
    private String initiativeType;
    private String duration;
    private Visibility visibility;
    private String description;
    private Neighborhood location;
    private Neighborhood creatorNeighborhood;
    private URL image;
    private String creator;

    private boolean success;
    private String message;
    private HashMap<String, Object> responseParam = new HashMap<>();

    public Initiative(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Initiative(String id, String title, String initiativeType, String duration, Visibility visibility,
                      String description, Neighborhood location, Neighborhood creatorNeighborhood,
                      URL image, String creator) {
        this.id = id;
        this.title = title;
        this.initiativeType = initiativeType;
        this.duration = duration;
        this.visibility = visibility;
        this.description = description;
        this.location = location;
        this.creatorNeighborhood = creatorNeighborhood;
        this.image = image;
        this.creator = creator;
        this.success = true;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public HashMap<String, Object> getResponseParam() {
        return responseParam;
    }

    public void setResponseParam(HashMap<String, Object> responseParam) {
        this.responseParam = responseParam;
    }

    public void putInitiativesList(List<Initiative> initiatives) {
        responseParam.put("initiatives", initiatives);
    }

    @SuppressWarnings("unchecked")
    public List<Initiative> getInitiativesList() {
        Object list = responseParam.get("initiatives");
        if (list instanceof List<?>) {
            return (List<Initiative>) list;
        }
        return List.of();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getInitiativeType() {
        return initiativeType;
    }

    public String getDuration() {
        return duration;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public String getDescription() {
        return description;
    }

    public Neighborhood getLocation() {
        return location;
    }

    public Neighborhood getCreatorNeighborhood() {
        return creatorNeighborhood;
    }

    public URL getImage() {
        return image;
    }

    public String getCreator() {
        return creator;
    }
}
