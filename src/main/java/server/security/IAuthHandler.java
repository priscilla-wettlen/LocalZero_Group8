package server.security;

import shared.Request;
import shared.Response;

public interface IAuthHandler {

    public IAuthHandler setNext(IAuthHandler next);
    public Response handle(Request request);


}
