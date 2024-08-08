package org.mude.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class ClaimsParser {
    @Value("${keycloak.secret.key}")
    private SecretKey secretKey;

    public Claims getClaims(String token) {
        return Jwts.parser().verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
