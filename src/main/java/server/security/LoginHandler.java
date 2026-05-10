package main.java.server.security;

import main.java.server.service.Coordinator;
import main.java.shared.Request;
import main.java.shared.Response;

public class LoginHandler implements IAuthHandler{
    private Coordinator coordinator;
    private IAuthHandler next =null;

    public LoginHandler(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public IAuthHandler setNext(IAuthHandler next) {
        this.next = next;
        return next;
    }

    @Override
    public Response handle(Request request) {
        return null;
    }
}
