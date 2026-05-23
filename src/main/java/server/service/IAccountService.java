package server.service;

import server.model.Neighborhood;
import server.model.Role;
import server.model.User;

import java.util.HashSet;

public interface IAccountService {

    User register(String name, Neighborhood neighborhood, String email, String password, String adminCode, String role);

    public User login(String username, String password);
}
