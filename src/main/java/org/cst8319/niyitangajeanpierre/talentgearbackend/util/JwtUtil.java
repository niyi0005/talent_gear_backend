package org.cst8319.niyitangajeanpierre.talentgearbackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private String secretKey = "mySecretKey"; // Secret key to sign JWTs

    // Generate JWT token
    public String generateToken(UserEntity user) {
        return Jwts.builder()
                .setSubject(user.getUsername())  // Subject is the username (can also include other user details)
                .claim("role", user.getRole().toString())  // Add role as a claim
                .setIssuedAt(new Date())  // Issue time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // 10 hours expiration
                .signWith(SignatureAlgorithm.HS256, secretKey)  // Signing the JWT token
                .compact();
    }

    // Validate JWT token
    public boolean validateToken(String token, String username) {
        try {
            // Parse the JWT token
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            // Extract the username from the token's claims
            String tokenUsername = claims.getSubject();

            // Compare the token's username with the provided username
            return (tokenUsername.equals(username) && !isTokenExpired(claims)); // Ensure the token is not expired
        } catch (JwtException | IllegalArgumentException e) {
            return false; // Token is invalid or expired
        }
    }

    // Extract username (or subject) from JWT token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from the JWT token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Helper method to check if the token is expired
    private boolean isTokenExpired(Claims claims) {
        Date expirationDate = claims.getExpiration();
        return expirationDate.before(new Date());
    }

    // Extract a specific claim (e.g., role, etc.) from the JWT token
    public <T> T extractClaim(String token, ClaimsResolver<T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.resolve(claims);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // Functional interface to extract claims
    public interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }
}
