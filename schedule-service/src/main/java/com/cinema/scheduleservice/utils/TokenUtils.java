package com.cinema.scheduleservice.utils;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

@Service
public class TokenUtils {


    public String getUsernameFromToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                SignedJWT signedJWT = SignedJWT.parse(token);
                return signedJWT.getJWTClaimsSet().getSubject();
            } catch (Exception e) {
                throw new RuntimeException("Invalid token", e);
            }
        }
        throw new IllegalArgumentException("Authorization header is missing or not valid");
    }
}