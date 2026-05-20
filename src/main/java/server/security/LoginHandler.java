package server.security;

import server.service.Coordinator;
import shared.Request;
import shared.Initiative;

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
    public Initiative handle(Request request) {
        return null;
    }
}
