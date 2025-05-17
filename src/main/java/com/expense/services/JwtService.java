package com.expense.services;

import com.expense.exceptions.JwtTokenInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey; //dalam format base64

    @Value("${security.jwt.expiration}")
    @Getter
    private long expirationMs;

    public String generateToken(UserDetails userDetails) throws JwtTokenInvalidException {
        return this.generateToken(userDetails, Map.of());
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> claims) throws JwtTokenInvalidException {
        return this.buildToken(userDetails, claims);
    }

    private String buildToken(UserDetails userDetails, Map<String, Object> claims) throws JwtTokenInvalidException {
        try {
            return Jwts.builder()
                    .claims()
                    .issuer("expense-tracker")
                    .subject(userDetails.getUsername())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + this.expirationMs))
                    .add(claims)
                    .and()
                    .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey)))
                    .compact();
        } catch (Exception ex) {
            throw new JwtTokenInvalidException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to generate jwt token", ex.getMessage());
        }
    }

    // membaca payload untuk mengecek siapa pemilik token JWT
    public String extractUsername(String token) throws JwtTokenInvalidException {
        Claims claims = this.extractClaims(token);
        return claims.getSubject();
    }

    // membaca semua isi payload token JWT
    public Claims extractClaims(String token) throws JwtTokenInvalidException {
        try {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey)))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenInvalidException(HttpStatus.UNAUTHORIZED, "The jwt token is expired", ex.getMessage());
        } catch (JwtException ex) {
            throw new JwtTokenInvalidException(HttpStatus.UNAUTHORIZED, "The jwt token is invalid", ex.getMessage());
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) throws JwtTokenInvalidException {
        String username = this.extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) throws JwtTokenInvalidException {
        Claims claims = this.extractClaims(token);
        return claims.getExpiration().before(new Date());
    }
}
