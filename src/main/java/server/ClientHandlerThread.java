package server;

import server.security.IAuthHandler;
import server.service.Coordinator;
import protocol.Request;
import protocol.Initiative;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandlerThread implements Runnable {
    private Socket socket = new Socket();
    private Coordinator coordinator;
    private IAuthHandler authChainStart;

    public ClientHandlerThread(Socket socket, Coordinator coordinator, IAuthHandler authChainStart) {
        this.socket = socket;
        this.coordinator = coordinator;
        this.authChainStart = authChainStart;
    }


    @Override
    public void run() {
        try (
                ObjectOutputStream outs =
                        new ObjectOutputStream(
                                socket.getOutputStream());
                ObjectInputStream ins =
                        new ObjectInputStream(
                                socket.getInputStream())
        ) {
            outs.flush();
            while (true) {
                Request request =
                        (Request) ins.readObject();
                Initiative response =
                        handleRequest(request);
                outs.writeObject(response);
                outs.flush();
            }

        } catch (IOException
                 | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




    private Initiative handleRequest(Request request){

        try{
            Initiative authChainResponse = authChainStart.handle(request);
            if (authChainResponse != null) {
                return authChainResponse;
            }
            return coordinator.processRequest(request);
        }finally{
            //end Session
        }

    }

}

