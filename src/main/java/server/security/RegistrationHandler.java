package server.security;

import server.service.Coordinator;
import shared.Request;
import shared.Response;

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
