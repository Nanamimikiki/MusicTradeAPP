//package org.mtapp.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//
//@Component
//public class ClaimsParser {
//
//    private final SecretKey secretKey;
//
//
//    public ClaimsParser(SecretKey secretKey) {
//        this.secretKey = secretKey;
//    }
//
//    public Claims getClaims(String token) {
//        return Jwts.parser()
//                .verifyWith(secretKey)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//}
