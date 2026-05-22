package shared;

import server.model.Neighborhood;
import server.model.Visibility;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;

public class Initiative implements Serializable {
    private String id;
    private String title;
    private String initiativeType;
    private String duration;
    private Visibility visibility;
    private String description;
    private Neighborhood location;
    private URL image;
    private String creator;

    private boolean success;
    private String message;
    private HashMap<String, Object> responseParam = new HashMap<>();

    public Initiative(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public Initiative(String id, String title, String initiativeType, String duration, Visibility visibility, String description, Neighborhood location, URL image, String creator) {
        this.id = id;
        this.title = title;
        this.initiativeType = initiativeType;
        this.duration = duration;
        this.visibility = visibility;
        this.description = description;
        this.location = location;
        this.image = image;
        this.creator = creator;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public HashMap<String, Object> getResponseParam() {
        return responseParam;}
    public void setResponseParam(HashMap<String, Object> responseParam) {
        this.responseParam = responseParam;
    }
}
