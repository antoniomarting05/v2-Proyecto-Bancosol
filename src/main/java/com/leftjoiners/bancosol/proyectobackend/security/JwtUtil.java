/*
Javier Urbaneja Benítez: 70%
IA: 30%

Documentación JWT: https://www.geeksforgeeks.org/springboot/spring-boot-3-0-jwt-authentication-with-spring-security-using-mysql-database/
*/

package com.leftjoiners.bancosol.proyectobackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;
    public String generateToken(String username, String rol, String nombre) {
        // Spring Security necesita que los roles tengan el prefijo "ROLE_"
        String rolFormateado = "ROLE_" + rol.toUpperCase();

        return Jwts.builder()
                .claim("rol", rolFormateado)
                .claim("nombre", nombre)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public String extractRol(String token) {
        try {
            return getClaims(token).get("rol", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String extractNombre(String token) {
        try {
            return getClaims(token).get("nombre", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            return !getClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }
}