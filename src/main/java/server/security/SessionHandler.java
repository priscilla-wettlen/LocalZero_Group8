package server.security;

import server.service.Coordinator;
import shared.Request;
import shared.Initiative;

public class SessionHandler implements IAuthHandler{
    private Coordinator coordinator;
    private IAuthHandler next= null;


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
