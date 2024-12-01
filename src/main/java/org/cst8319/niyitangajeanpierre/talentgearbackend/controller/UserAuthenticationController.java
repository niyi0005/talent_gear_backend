package org.cst8319.niyitangajeanpierre.talentgearbackend.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.UserLoginDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.config.CustomUserDetailsService;
import org.cst8319.niyitangajeanpierre.talentgearbackend.config.JwtUtil;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.UserRepository;
//import org.cst8319.niyitangajeanpierre.talentgearbackend.config.JwtUtil;
import org.cst8319.niyitangajeanpierre.talentgearbackend.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationController {

    @Autowired
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserAuthenticationService userAuthenticationService;


    // Login endpoint
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        log.info("Login attempt");
        try {
            String jwt = userAuthenticationService.authenticateUser(userLoginDto);

            // Return a successful response with the JWT token
            return ResponseEntity.ok().body(jwt);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    // Logout endpoint (client will discard the JWT token)
    @PostMapping("/logout")
    public String logout() {
        return "Logged out successfully";  // Client will discard the JWT token
    }




}
