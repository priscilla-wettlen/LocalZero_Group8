package server.security;

import protocol.Request;
import protocol.Initiative;

public interface IAuthHandler {

    public IAuthHandler setNext(IAuthHandler next);
    public Initiative handle(Request request);


}
