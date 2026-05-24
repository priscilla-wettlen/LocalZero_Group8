package server.service;

import server.model.Neighborhood;
import server.model.User;

import java.util.List;

public interface IAccountService {

    User register(String name, Neighborhood neighborhood, String email, String password, String adminCode, String role);

    public User login(String username, String password);

    List<User> getUsersByNeighborhood(Neighborhood neighborhood, String excludedUserId);
}
