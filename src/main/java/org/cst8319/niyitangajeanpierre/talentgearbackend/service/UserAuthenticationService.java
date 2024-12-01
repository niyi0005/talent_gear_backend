package org.cst8319.niyitangajeanpierre.talentgearbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.UserLoginDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.config.CustomAuthenticationToken;
import org.cst8319.niyitangajeanpierre.talentgearbackend.config.CustomUserDetailsService;
import org.cst8319.niyitangajeanpierre.talentgearbackend.config.JwtAuthentication;
import org.cst8319.niyitangajeanpierre.talentgearbackend.config.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationService {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    public String authenticateUser(@RequestBody UserLoginDto userLoginDto) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userLoginDto.getUsername());

        if(!userLoginDto.getUsername().equals(userDetails.getUsername())) {
            throw new BadCredentialsException("Invalid username");
        }

        if (!userLoginDto.getPassword().equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        try {
            Authentication authentication = new JwtAuthentication(userDetails);

            log.info("Authenticated user:{} ", userDetails.getUsername());

            String token = jwtUtil.generateToken(authentication);
            log.info("Generated token: {} ", token);
            log.info("JWT token generated for user: {} ", userDetails.getUsername());
            return token;

        } catch (Exception e) {
            // Handle authentication failure
            log.error("Authentication failed for user:{} ", userLoginDto.getUsername(), e);
            throw new BadCredentialsException("Authentication failed for user: " + userLoginDto.getUsername());
        }

        // Create a custom authentication token with proper authorities
//        CustomAuthenticationToken authenticationToken = new CustomAuthenticationToken(
//                userDetails.getUsername(),
//                userDetails.getPassword(),
//                userDetails.getAuthorities()
//        );

//        authenticationToken.setDetails(userDetails);
//        log.info("Authentication token: " + authenticationToken);
//        return authenticationToken;
    }


}
