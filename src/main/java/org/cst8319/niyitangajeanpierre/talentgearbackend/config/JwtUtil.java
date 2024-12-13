package org.cst8319.niyitangajeanpierre.talentgearbackend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
public class JwtUtil {

//    private final String SECRET_KEY = "mySecretKey"; // Secret key to sign JWTs
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("mySecretKey".getBytes());


    // Generate JWT token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        log.debug("Authentication object {}:", authentication.getName());

        // Extract roles from the authentication object
        List<String> authorities = (authentication.getAuthorities() !=null) ?
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)  // Convert to role names (e.g., "ROLE_JOB_SEEKER")
                        .collect(Collectors.toList())
                : List.of();

        String token = null;
        try {
            token = Jwts.builder()
                    .setSubject(username)  // Subject is the username (can also include other user details)
                    .claim("authorities", authorities)  // Add authorities as a claim
                    .setIssuedAt(new Date())  // Issue time
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))  // One hour expiration
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)  // Signing the JWT token
                    .compact();
            log.debug("Generated JWT token inside JwtUtil: {}", token);
        } catch (JwtException e) {
            log.error("Error generating JWT token", e);
        } finally {
            if (token != null) {
                System.out.println("Generated JWT token: "+ token);
            } else {
                System.out.println("Failed to generate JWT token");
            }
        }
        return token;
    }

    // Validate JWT token
    public boolean validateToken(String token, String username) {
        try {
            // Parse the JWT token
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
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
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // Functional interface to extract claims
    public interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
