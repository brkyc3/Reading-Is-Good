package com.readingisgood.customerservice.service.jwt;

import io.jsonwebtoken.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {
    @Value("${jwt.secretKey}")
    private String key;

    @Value("${jwt.expireTimeMs}")
    private long ttlMillis;


    public String createJWT(String subject) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        Date now = new Date();
        Date expiration = new Date(now.getTime()+ttlMillis);

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expiration)
                .setSubject(subject)
                .signWith(signatureAlgorithm, key)
                .compact();
    }

    public String parseJWT(String jwt) {

        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
