package main.java.server.security;

import main.java.shared.Request;
import main.java.shared.Response;

public interface IAuthHandler {

    public IAuthHandler setNext(IAuthHandler next);
    public Response handle(Request request);


}
