package server;

import server.security.IAuthHandler;
import server.service.Coordinator;

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
        //input and output stream logic
    }
}

