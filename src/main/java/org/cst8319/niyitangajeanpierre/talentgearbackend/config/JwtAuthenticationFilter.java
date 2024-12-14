package org.cst8319.niyitangajeanpierre.talentgearbackend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("Request inside JWT Authentication Filter: {}", request);

        String token = extractJwtFromRequest(request);
        log.debug("Token inside JwtAuthenticationFilter: {} ", token);

        if (StringUtils.hasText(token)) {

                try{
                String username = jwtUtil.extractUsername(token);
                log.info("Found username: {}", username);

                // Validate token and username match
                if (jwtUtil.validateToken(token, username)) {

                    // Create authentication object
                    Authentication authentication = customUserDetailsService.createJwtAuthentication(username);

                    // Set the authentication context
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("Authentication successful for user: {}", username);
                } else {
                    throw new BadCredentialsException("Invalid or expired token");
                }
        } catch (BadCredentialsException e) {
            log.error("Authentication failed: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
            throw new BadCredentialsException("Invalid JWT token", e);
        }

        } else{
            log.warn("No JWT token found in request");
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }


    // Extracts JWT token from Authorization header
    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.debug("Authorization header: {}", bearerToken); // Add debug log to check header contents
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Extract token after "Bearer "
        }
        return null;
    }
}
