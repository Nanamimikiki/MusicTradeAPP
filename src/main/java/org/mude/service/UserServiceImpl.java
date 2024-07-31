package org.mude.service;

import lombok.extern.slf4j.Slf4j;
import org.mude.model.Role;
import org.mude.model.Status;
import org.mude.model.User;
import org.mude.repos.UserRepository;
import org.mude.service.i.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.mude.repos.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findRoleByName("ROLE_USER").orElse(null);
        if (role == null) {
            log.info("In registerUser role - {} not found", "ROLE_USER");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        user.setStatus(Status.ACTIVE);
        User registeredUser = userRepository.save(user);
        log.info("In registerUser: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public User loginUser(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (foundUser == null) {
            log.info("In loginUser - user with username {} was not found", user.getUsername());
            return null;
        }
        if (!passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            log.info("In loginUser - password does not match for user {}", user.getUsername());
            return null;
        }
        log.info("In loginUser - user {} successfully logged in", foundUser);
        return foundUser;
    }


    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("In getAllUsers - {} users found", users.size());
        return users;
    }

    @Override
    public List<User> getBannedUsers() {
        List<User> bannedUsers = userRepository.findAll().stream()
                .filter(user -> user.getStatus() == Status.BANNED).toList();
        log.info("In getBannedUsers - {} banned users found", bannedUsers.size());
        return bannedUsers;
    }

    @Override
    public List<User> getUsersWithRole(String role) {
        List<User> usersWithRole = userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream().anyMatch(Role -> Role.getName().equals(role))).toList();
        log.info("In getUsersWithRole - {} users with role {} found", usersWithRole.size(), role);
        return usersWithRole;
    }

    @Override
    public User getUser(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            log.info("In getUser - user with username {} was not found", username);
            return null;
        }
        log.info("In getUser - user {} was found by username {}", user, username);
        return user;
    }

    @Override
    public User getUser(UUID userID) {
        User user = userRepository.findById(userID).orElse(null);
        if (user == null) {
            log.info("In getUser - user with ID {} was not found", userID);
            return null;
        }
        return user;
    }


    @Override
    public void banUser(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            log.info("In banUser - user with username {} was not found", username);
            return;
        }
        user.setStatus(Status.BANNED);
        userRepository.save(user);
        log.info("In banUser - user {} was banned", username);
    }

    @Override
    public void banUser(UUID userID) {
        User user = userRepository.findById(userID).orElse(null);
        if (user == null) {
            log.info("In banUser - user with ID {} was not found", userID);
            return;
        }
        user.setStatus(Status.BANNED);
        userRepository.save(user);
        log.info("In banUser - user with ID {} was banned", userID);
    }

    @Override
    public void unbanUser(UUID userID) {
        User user = userRepository.findById(userID).orElse(null);
        if (user == null) {
            log.info("In unbanUser - user with ID {} was not found", userID);
            return;
        }
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
        log.info("In unbanUser - user with ID {} was unbanned", userID);
    }

    @Override
    public void unbanUser(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            log.info("In unbanUser - user with username {} was not found", username);
            return;
        }
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
        log.info("In unbanUser - user {} was unbanned", username);
    }

    @Override
    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            if (user.getPassword().equals(passwordEncoder.encode(password))) {
                log.info("User {} logged in successfully", username);
                return user;
            }
            log.info("In loginUser - password does not match for user {}", username);
            return null;
        }
        log.info("In loginUser - user with username {} was not found", username);
        return null;
    }
}
