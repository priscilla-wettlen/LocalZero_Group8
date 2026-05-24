package protocol;

import java.io.Serializable;
import java.util.HashMap;

public class Response implements Serializable {
    private boolean success;
    private String message;
    private HashMap<String, Object> responseParam = new HashMap<>();


    public Response(boolean success, String message){
        this.success = success;
        this.message = message;
        this.responseParam = new HashMap<>();
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

    public void addParam(String key, Object value) {
        responseParam.put(key, value);
    }


}