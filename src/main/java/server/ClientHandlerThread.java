package server;

import server.security.IAuthHandler;
import server.security.SessionHandler;
import server.service.Coordinator;
import shared.Request;
import shared.Response;

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
        try(ObjectInputStream ins = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outs = new ObjectOutputStream(socket.getOutputStream())){
            while(true)
            {
                Request request = (Request) ins.readObject();
                Response response = handleRequest(request);
                outs.writeObject(response);
            }
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    private Response handleRequest(Request request){
        try{
            Response authChainResponse = authChainStart.handle(request);
            if (authChainResponse != null) {
                return authChainResponse;
            }
            return coordinator.processRequest(request);
        }finally{
            //end Session
        }

    }

}

