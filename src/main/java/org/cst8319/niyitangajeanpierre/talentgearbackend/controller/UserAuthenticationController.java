package org.cst8319.niyitangajeanpierre.talentgearbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.UserLoginDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.UserRegisterDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.UserEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationController {


    private final UserAuthenticationService userAuthenticationService;



    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        log.debug("Login endpoint hit");
        log.debug("Login username inside controller: {}", userLoginDto.getUsername());
        try {
            String jwt = userAuthenticationService.authenticateUser(userLoginDto);
            log.debug("JWT inside the controller: {}", jwt);

            // Return a successful response with the JWT token
            return ResponseEntity.ok().body(jwt);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    // Logout endpoint (client will discard the JWT token)
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        return "Logged out successfully";  // Client will discard the JWT token
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        log.debug("Register endpoint hit");
        log.debug("Inside Controller: Registering user {}", userRegisterDto.getUsername());
        try {
            String jwt = userAuthenticationService.registerAndAuthenticateUser(userRegisterDto);
            log.debug("JWT token generated for new user {}", jwt);
            return ResponseEntity.status(HttpStatus.CREATED).body(jwt);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Registration failed: Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while registering user");
        }
    }

//    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
//        try {
//         UserEntity registeredUser = userAuthenticationService.createUser(userRegisterDto);
//
//         // Authenticate the user
//            UserLoginDto userLoginDto = new UserLoginDto();
//            userLoginDto.setUsername(registeredUser.getUsername());
//            userLoginDto.setPassword(registeredUser.getPassword());
//            String jwt = userAuthenticationService.authenticateUser(userLoginDto);
//         return ResponseEntity.status(HttpStatus.CREATED).body(jwt);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while registering user");
//        }
//    }
}
