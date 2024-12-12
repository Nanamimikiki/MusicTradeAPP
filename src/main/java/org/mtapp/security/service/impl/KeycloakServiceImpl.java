package org.mtapp.security.service.impl;

import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mtapp.security.service.KeycloakService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@Slf4j
@Service
public class KeycloakServiceImpl implements KeycloakService {

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${keycloak.resource}")
    private String keycloakClientId;

    @Value("${keycloak.credentials.secret}")
    private String keycloakClientSecret;

    @Value("${keycloak-admin-username}")
    private String keycloakAdminUsername;

    @Value("${keycloak-admin-password}")
    private String keycloakAdminPassword;

    private RestTemplate restTemplate;

    @Override
    public Keycloak keycloakBuilder() {
        try {
            return KeycloakBuilder.builder()
                    .serverUrl(keycloakServerUrl)
                    .realm(keycloakRealm)
                    .clientId(keycloakClientId)
                    .clientSecret(keycloakClientSecret)
                    .username(keycloakAdminUsername)
                    .password(keycloakAdminPassword)
                    .build();
        } catch (Exception e) {
            log.error("Error while creating Keycloak client", e);
            return null;
        }
    }

    @Override
    public void registerUser(String username, String password) throws Exception {
        try (Keycloak keycloak = keycloakBuilder()) {
            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setUsername(username);
            userRepresentation.setEnabled(true);
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(password);
            userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
            try (Response response = keycloak.realm(keycloakRealm).users().create(userRepresentation)) {
                if (response.getStatus() != 201) {
                    log.error("Failed to create user: {}", response.readEntity(String.class));
                    throw new RuntimeException("Failed to create user");
                }
            }
        } catch (Exception e) {
            log.error("Error while registering user", e);
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> getTokenResponse(String username, String password) {
        String tokenEndpoint = keycloakServerUrl + "/token";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("grant_type", "password");
        bodyParams.put("client_id", keycloakClientId);
        bodyParams.put("client_secret", keycloakClientSecret);
        bodyParams.put("username", username);
        bodyParams.put("password", password);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(bodyParams, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return (Map<String, String>) response.getBody();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }

    @Override
    public void revokeToken(String token) {
        String revokeEndpoint = keycloakServerUrl + "/logout";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(revokeEndpoint, entity, Void.class);

        if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Token revocation failed");
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> fetchUserInfo(String token) {
        String userInfoEndpoint = keycloakServerUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/userinfo";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(userInfoEndpoint, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            log.error("Failed to fetch user info: {}", response.getStatusCode());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token or user not found");
        }
    }

    @Override
    public String getCurrentUserToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user");
        }
    }
}