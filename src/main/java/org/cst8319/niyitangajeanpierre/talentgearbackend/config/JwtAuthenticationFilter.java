package org.cst8319.niyitangajeanpierre.talentgearbackend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;





    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractJwtFromRequest(request);

        if (StringUtils.hasText(token)) {
            String username = jwtUtil.extractUsername(token);

            // Validate token and username match
            if (jwtUtil.validateToken(token, username)) {

                //Use CustomAuthenticationToken instead of UsernamePasswordAuthenticationToken since does not expose a setDetails() method
//                CustomAuthenticationToken authentication = new CustomAuthenticationToken(
//                        username, null // You can set authorities if needed
//                );
                Authentication authentication = customUserDetailsService.createJwtAuthentication(username);

//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null);


                // Set details on the authentication object
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication context
//                SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(authentication));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new BadCredentialsException("Invalid or expired token");
            }
        }

        filterChain.doFilter(request, response); // Continue the filter chain
    }

    // Extracts JWT token from Authorization header
    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Extract token after "Bearer "
        }
        return null;
    }
}
