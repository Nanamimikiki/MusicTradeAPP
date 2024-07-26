//package org.mude.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Value;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//
//public class JwtUtil {
//    @Value("${keycloak.secret.key}")
//    private SecretKey secretKey;
//
//    public Claims getClaims(String token) {
//        return Jwts.parser().verifyWith(secretKey)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//
//    public String getUsername(String token) {
//        return getClaims(token).getSubject();
//    }
//
//    public Date getExpiration(String token) {
//        return getClaims(token).getExpiration();
//    }
//
//    public boolean isTokenExpired(String token) {
//        return getExpiration(token).before(new Date());
//    }
//
//    public boolean validateToken(String token, String username) {
//        final String tokenUsername = getUsername(token);
//        return (tokenUsername.equals(username) && !isTokenExpired(token));
//    }
//}
