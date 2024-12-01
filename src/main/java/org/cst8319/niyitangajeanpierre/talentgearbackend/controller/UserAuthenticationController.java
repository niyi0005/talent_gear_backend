package org.cst8319.niyitangajeanpierre.talentgearbackend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.UserLoginDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationController {

    @Autowired
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
