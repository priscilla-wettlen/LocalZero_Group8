package client;

import shared.Request;
import shared.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ClientConnectionManager {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String token;

    public ClientConnectionManager(int port, String host) throws IOException {
        this.socket = new Socket(host, port);

        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
    }


    public HashMap<String, Object> sendRequest(Request request){
        HashMap<String, Object> responseParam = new HashMap<>();
        try{
            out.writeObject(request);
            Response response = (Response) in.readObject();
            if(response.isSuccess()){
                responseParam = response.getResponseParam();
            }

        }catch(ClassNotFoundException|IOException|RuntimeException e){
            System.out.println("Error sending request");
        }
        return responseParam;
    }


    public void closeConnection() throws IOException {
        socket.close();
    }

    public void setAuthToken(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}
