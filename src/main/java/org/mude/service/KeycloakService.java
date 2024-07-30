package org.mude.service;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mude.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Slf4j
@Service
public class KeycloakService {

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

    public void registerUser(User user) {
        try (Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm("MusicTradeAppRealm")
                .clientId("admin-cli")
                .username(keycloakAdminUsername)
                .password(keycloakAdminPassword)
                .build()) {

            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setUsername(user.getUsername());
            userRepresentation.setEmail(user.getEmail());
            userRepresentation.setCreatedTimestamp(user.getCreatedAt().getTime());
            userRepresentation.setEnabled(true);

            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(user.getPassword());
            userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

            try (Response response = keycloak.realm(keycloakRealm).users().create(userRepresentation)) {
                if (response.getStatus() != 201) {
                    log.error("Failed to create user: {}", response.readEntity(String.class));
                    throw new RuntimeException("Failed to create user");
                }
            }
        }
    }
}


