package client;

import protocol.Request;
import protocol.Initiative;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnectionManager {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String token;

    public ClientConnectionManager(String host,
                                   int port)
            throws IOException {

        this.socket = new Socket(host, port);

        this.out = new ObjectOutputStream(
                socket.getOutputStream());

        out.flush();

        this.in = new ObjectInputStream(
                socket.getInputStream());
    }


    public Initiative sendRequest(Request request) {
        Initiative response = null;
        try {
            out.writeObject(request);
            out.flush();
            response = (Initiative) in.readObject();
        } catch (ClassNotFoundException
                 | IOException
                 | RuntimeException e) {
            System.out.println("Error sending request");

            e.printStackTrace();
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
