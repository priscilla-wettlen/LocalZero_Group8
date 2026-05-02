package main.java.server;

import main.java.server.service.Coordinator;
import main.java.server.service.ICoordinator;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandlerThread implements Runnable {
    private Socket socket = new Socket();
    private ICoordinator accessProxy;
    private Coordinator coordinator;


    @Override
    public void run() {
        //input and output stream logic
    }
}
