package server.security;

import server.service.Coordinator;
import shared.Request;
import shared.Response;

public class SessionHandler implements IAuthHandler{
    private Coordinator coordinator;
    private IAuthHandler next= null;


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
