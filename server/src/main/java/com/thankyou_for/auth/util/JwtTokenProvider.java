package com.thankyou_for.auth.util;

import com.thankyou_for.auth.dto.IdAndEmail;
import com.thankyou_for.auth.exception.InvalidTokenException;
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

    public String createRefundToken(String merchantUid) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .claim("merchantUid", merchantUid)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secreteKey)
                .compact();
    }

    public String createAdminToken(String id) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .claim("id", id)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secreteKey)
                .compact();
    }

    public String createMasterToken(long id, String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 24 * 60 * 60 * 1000); // 24시간

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
            Jwts.parser().setSigningKey(secreteKey).parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }

    public IdAndEmail findIdAndEmailFromToken(String token) {
        Claims claims = claims(token);
        return new IdAndEmail(
                claims.get("id", Long.class),
                claims.get("email", String.class));
    }

    public String findMerchantUidFromToken(String token) {
        Claims claims = claims(token);
        return claims.get("merchantUid", String.class);
    }

    private Claims claims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secreteKey).parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new InvalidTokenException();
        }
    }
}
