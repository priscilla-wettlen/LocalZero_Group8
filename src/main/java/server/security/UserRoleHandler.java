package server.security;

import server.service.Coordinator;
import protocol.Request;
import protocol.Initiative;

public class UserRoleHandler implements IAuthHandler{
    private Coordinator coordinator;
    private IAuthHandler next = null;

    public UserRoleHandler(Coordinator coordinator) {
        this.coordinator = coordinator;
    }


    @Override
    public IAuthHandler setNext(IAuthHandler next) {
        return next;

    }

    @Override
    public Initiative handle(Request request) {
        return null;
    }
}
