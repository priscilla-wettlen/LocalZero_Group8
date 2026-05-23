package server.security;

import server.service.Coordinator;
import protocol.Request;
import protocol.Initiative;

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
    public Initiative handle(Request request) {
        return null;
    }
}
