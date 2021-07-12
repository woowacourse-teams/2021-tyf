package com.example.tyfserver.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secreteKey}")
    private String secreteKey;

    @Value("${jwt.expire-length}")
    private long validityInMilliseconds;

    public String createToken(long id, String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .claim("id", id)
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secreteKey)
                .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secreteKey).parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException(); //todo: 예외 컨벤션 정하기
        }
    }

    public Long findIdByToken(String token) {
        return claims(token)
                .get("id", Long.class);
    }

    public String findEmailByToken(String token) {
        return claims(token)
                .get("email", String.class);
    }

    private Claims claims(String token) {
        return Jwts.parser()
                .setSigningKey(secreteKey).parseClaimsJws(token)
                .getBody();
    }
}
