package com.example.login.util;



import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;



import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
@Component
public class JwtUtil {
    private final String secret = "pp";

    // Generate a token



    public String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Extract token part
        }
        return null; // No token provided
    }
    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .sign(Algorithm.HMAC256(secret));
    }

    // Validate and decode a token
//    public String validateToken(String token) {
//        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secret))
//                .build()
//                .verify(token);
//        return jwt.getSubject();
//    }

    public boolean validateToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token);
            return true; // Token is valid
        } catch (Exception e) {
            return false; // Token is invalid
        }
    }


    public String extractUsername(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token);
        return decodedJWT.getSubject();
    }
}
