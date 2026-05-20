package server.security;

import shared.Request;
import shared.Initiative;

public interface IAuthHandler {

    public IAuthHandler setNext(IAuthHandler next);
    public Initiative handle(Request request);


}
