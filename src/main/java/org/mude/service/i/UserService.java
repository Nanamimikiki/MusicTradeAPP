package org.mude.service.i;

import org.mude.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface UserService {
    User registerUser(User user);

    User loginUser(User user);

    List<User> getAllUsers();

    List<User> getBannedUsers();

    List<User> getUsersWithRole(String role);

    User getUser(String username);

    User getUser(UUID userID);
    User getUserByEmail(String email);

    void banUser(String username);

    void banUser(UUID userID);

    void unbanUser(UUID userID);

    void unbanUser(String username);

    List<User> findUsersRegisteredBetween(LocalDate start, LocalDate end);
    User loginUser(String username, String password);

    void updateUserEmail(UUID userId, String newEmail);

    void deleteUser(UUID userId);
}
