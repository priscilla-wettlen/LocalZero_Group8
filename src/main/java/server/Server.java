package server;

import server.service.Coordinator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private int port;
    private Coordinator coordinator;
    private IAuthHandler authChainStart;
    public Server(int port) {
        this.port = port;
        this.coordinator = new Coordinator();
        RegistrationHandler regHandler= new RegistrationHandler(coordinator);
        LoginHandler loginHandler = new LoginHandler(coordinator);
        SessionHandler sessionHandler = new SessionHandler();
        UserRoleHandler userRoleHandler = new UserRoleHandler(coordinator);
        setAuthChain(regHandler,loginHandler,sessionHandler,userRoleHandler);
    }

    /// Ok, this is what sets the chain of responsibility! We set the next step for all the
    /// links in the chain and pass to the client Threads a reference to the first link in the chain

    private void setAuthChain(RegistrationHandler regHandler, LoginHandler loginHandler,
                              SessionHandler sessionHandler, UserRoleHandler userRoleHandler) {

        regHandler.setNext(loginHandler).setNext(sessionHandler).setNext(userRoleHandler);
        this.authChainStart = regHandler;
    }


    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started. Using port " + port);

            while (true){
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandlerThread(clientSocket, coordinator, authChainStart)).start();
            }
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Server start failed");
        }}


    /// Can later be put in another class!!
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Server(1080).startServer();
    }


    }



