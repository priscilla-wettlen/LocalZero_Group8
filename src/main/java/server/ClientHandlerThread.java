package main.java.server;

import main.java.server.security.IAuthHandler;
import main.java.server.service.Coordinator;
import main.java.server.service.ICoordinator;

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
        //input and output stream logic
    }
}

