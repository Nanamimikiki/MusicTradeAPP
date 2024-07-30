package org.mude.service;

import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mude.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Getter
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

    private void getUser() throws Exception {

    }

    public Keycloak keycloakBuilder() {
            try {
                return KeycloakBuilder.builder()
                        .serverUrl(keycloakServerUrl)
                        .realm("MusicTradeAppRealm")
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


        public void registerUser(User user) throws Exception {
            Keycloak keycloakBuilder = keycloakBuilder();
            try {

                UserRepresentation userRepresentation = new UserRepresentation();
                userRepresentation.setUsername(userRepresentation.getUsername());
                userRepresentation.setEmail(userRepresentation.getEmail());
                userRepresentation.setCreatedTimestamp(userRepresentation.getCreatedTimestamp());
                userRepresentation.setEnabled(true);
                CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
                userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
                credentialRepresentation = new CredentialRepresentation();
                credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
                credentialRepresentation.setValue(user.getPassword());


                try (Response response = keycloakBuilder.realm(keycloakRealm).users().create(userRepresentation)) {
                    if (response.getStatus() != 201) {
                        log.error("Failed to create user: {}", response.readEntity(String.class));
                        throw new RuntimeException("Failed to create user");
                    }
                }
            } catch (Exception e) {
                log.error("Error while registering user", e);
            }
            keycloakBuilder.close();
        }
    }


