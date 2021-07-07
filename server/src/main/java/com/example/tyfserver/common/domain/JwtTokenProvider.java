package com.example.tyfserver.common.domain;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secreteKey}")
    private String secreteKey;

    @Value("${jwt.expire-length}")
    private long validityInMilliseconds;

    public String createToken(String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
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

    public String findEmailByToken(String token) {
        return Jwts.parser()
            .setSigningKey(secreteKey).parseClaimsJws(token)
            .getBody()
            .get("email", String.class);
    }
}
