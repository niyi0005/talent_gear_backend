package org.cst8319.niyitangajeanpierre.talentgearbackend.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.UserLoginDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.UserRegisterDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.config.CustomUserDetailsService;
import org.cst8319.niyitangajeanpierre.talentgearbackend.config.JwtAuthentication;
import org.cst8319.niyitangajeanpierre.talentgearbackend.config.JwtUtil;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.RoleEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.UserEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.RoleRepository;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationService {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public String authenticateUser(@RequestBody UserLoginDto userLoginDto) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userLoginDto.getUsername());

        if(!userLoginDto.getUsername().equals(userDetails.getUsername())) {
            throw new BadCredentialsException("Invalid username");
        }
        try {

            if (!bCryptPasswordEncoder.matches(userLoginDto.getPassword(), userDetails.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }

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
    }


    public UserEntity createUser(@RequestBody UserRegisterDto userRegisterDto) {
        log.info("Register attempt user {}", userRegisterDto.getUsername());
        log.info("Register attempt user {}", userRegisterDto.getPassword());
        log.info("Register attempt user {}", userRegisterDto.getRoles());

        // Create a new user entity
        UserEntity newUser = new UserEntity();
        newUser.setUsername(userRegisterDto.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(userRegisterDto.getPassword()));
        newUser.setEmail(userRegisterDto.getEmail());
        newUser.setPhoneNumber(userRegisterDto.getPhoneNumber());
        newUser.setAddress(userRegisterDto.getAddress());
        newUser.setBio(userRegisterDto.getBio());
        newUser.setWebsite(userRegisterDto.getWebsite());
        newUser.setResumeUrl(userRegisterDto.getResumeUrl());
        newUser.setCreatedAt(LocalDateTime.now());

        // Validate if the roles exist in the database
        Optional<RoleEntity> optionalRoleEntity = roleRepository.findByName(userRegisterDto.getRoles());
        if(optionalRoleEntity.isPresent()) {
            RoleEntity roleEntity = optionalRoleEntity.get();
            newUser.setRoles(new HashSet<>(Collections.singletonList(roleEntity)));
        } else {
            throw new IllegalArgumentException("Invalid role" + userRegisterDto.getRoles());
        }
        return userRepository.save(newUser);
    }
}
