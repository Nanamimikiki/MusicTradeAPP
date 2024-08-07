package org.mude.rest;

import lombok.extern.slf4j.Slf4j;
import org.mude.service.impl.KeycloakServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/keycloak")
public class KeycloakRestController {
    @Autowired
    private KeycloakServiceImpl keycloakService;
    private static final String keycloakServerUrl = "http://localhost:8080/auth/realms/yourrealm/protocol/openid-connect";
    private static final String CLIENT_ID = "your-client-id";
    private static final String CLIENT_SECRET = "${keycloak.secret.key}";

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String username, @RequestParam String password) {
        try {
            log.info("User {} is attempting to log in.", username);
            Map<String, String> tokenResponse = keycloakService.getTokenResponse(username, password);
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            log.error("Login failed for user {}: {}", username, e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login failed", e);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password) {
        log.info("Attempting to register user {}.", username);
        try {
            keycloakService.registerUser(username, password);
            return ResponseEntity.ok("User " + username + " registered successfully.");
        } catch (Exception e) {
            log.error("Registration failed for user {}: {}", username, e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Registration failed", e);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        try {
            log.info("User is logging out.");
            String token = keycloakService.getCurrentUserToken();
            keycloakService.revokeToken(token);
            return ResponseEntity.ok("Logout successfully.");
        } catch (Exception e) {
            log.error("Logout failed: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Logout failed", e);
        }
    }

    @GetMapping("/user-info")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, Object>> getUserInfo() {
        try {
            log.info("Fetching user information.");
            String token = keycloakService.getCurrentUserToken();
            Map<String, Object> userInfo = keycloakService.fetchUserInfo(token);
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            log.error("Failed to fetch user info: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Failed to fetch user info", e);
        }
    }

}


