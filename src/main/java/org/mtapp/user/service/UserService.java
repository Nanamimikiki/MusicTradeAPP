package org.mtapp.user.service;

import org.mtapp.user.dto.UserDto;
import org.mtapp.user.model.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface UserService {
    User registerUser(User user);

    User loginUser(String username, String password);

    List<User> getAllUsers();

    List<User> getBannedUsers();

    List<User> getUsersWithRole(String role);

    List<User> findUsersRegisteredBetween(Date start, Date end);

    User getUser(String username);

    User getUser(UUID userID);

    User getUserByEmail(String email);

    void banUser(String username);

    void banUser(UUID userID);

    void unbanUser(UUID userID);

    void unbanUser(String username);
    public UserDto toUserDto(User user);


    void updateUserEmail(UUID userId, String newEmail);

    void deleteUser(UUID userId);
}
