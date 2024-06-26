package org.mude.service;

import org.mude.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User registerUser(User user);

    List<User> getAllUsers();

    List<User> getBannedUsers();

    List<User> getUsersWithRole(String role);

    User getUser(String username);

    User getUser(UUID userID);

    void banUser(String username);

    void banUser(UUID userID);

    void unbanUser(UUID userID);

    void unbanUser(String username);

}
