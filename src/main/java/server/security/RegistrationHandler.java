package main.java.server.security;

import main.java.server.service.Coordinator;
import main.java.shared.Request;
import main.java.shared.Response;

public class RegistrationHandler implements IAuthHandler{
    private Coordinator coordinator;
    private IAuthHandler next = null;

    public RegistrationHandler(Coordinator coordinator) {
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
