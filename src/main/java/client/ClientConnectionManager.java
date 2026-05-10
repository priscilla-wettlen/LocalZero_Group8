package client;

import shared.Request;
import shared.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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


    public Response sendRequest(Request request){
        Response response = null;
        try{
            out.writeObject(request);
            response = (Response) in.readObject();

        }catch(ClassNotFoundException|IOException|RuntimeException e){
            System.out.println("Error sending request");
        }
        return response;
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
