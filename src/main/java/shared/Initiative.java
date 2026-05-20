package shared;

import server.model.Neighborhood;
import server.model.Visibility;

import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;

public class Initiative implements Serializable {
    private boolean success;
    private String message;
    private HashMap<String, Object> responseParam = new HashMap<>();


    public Initiative(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public Initiative(String id, String title, String initiativeType, String duration, Visibility visibility, String description, Neighborhood location, URL image, String creator) {
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
