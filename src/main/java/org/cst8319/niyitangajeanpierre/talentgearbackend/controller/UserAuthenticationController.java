package org.cst8319.niyitangajeanpierre.talentgearbackend.controller;

import lombok.RequiredArgsConstructor;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.UserEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.UserRepository;
import org.cst8319.niyitangajeanpierre.talentgearbackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserAuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    // Login endpoint
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest loginRequest) {
//        UserEntity user = userRepository.findByUsername(loginRequest.getUsername());
//
//        // Check if user exists and password matches
//        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//            return jwtUtil.generateToken(user);  // Generate and return JWT token
//        } else {
//            throw new RuntimeException("Invalid username or password");
//        }
//    }

    // Logout endpoint (client will discard the JWT token)
    @PostMapping("/logout")
    public String logout() {
        return "Logged out successfully";  // Client will discard the JWT token
    }




}
