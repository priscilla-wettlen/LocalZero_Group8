package server.service;

import server.model.User;

public interface IAccountService {

    public User register(String username, String password);
    public String login(String username, String password);
}
