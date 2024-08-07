package org.mude.service;

import org.keycloak.admin.client.Keycloak;

import java.util.Map;

public interface KeycloakService {
    Map<String, String> getTokenResponse(String username, String password);

    void revokeToken(String token);
    Map<String, Object> fetchUserInfo(String token);

    Keycloak keycloakBuilder();

    void registerUser(String username, String password) throws Exception;

    String getCurrentUserToken();
}
