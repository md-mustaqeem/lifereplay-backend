package com.life.ai.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET =
            "my-secret-key-my-secret-key-my-secret-key"; // min 32 chars

    //Generate Token
    public String generateToken(String mobile) {
        return Jwts.builder()                                    // ① Token banana start
                .setSubject(mobile)                      // mobile no me token store huwa
                .setIssuedAt(new Date())               // current date set kiya
                .setExpiration(                        // expery date set kiya
                        new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)
                )
                .signWith(
                        Keys.hmacShaKeyFor(SECRET.getBytes()),        //secret key se sigh huwa
                        SignatureAlgorithm.HS256
                )
                .compact();                  // final token generate huwa
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
