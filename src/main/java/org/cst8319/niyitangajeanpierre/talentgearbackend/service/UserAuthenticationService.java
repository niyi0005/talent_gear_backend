package org.cst8319.niyitangajeanpierre.talentgearbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.PasswordResetDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.UserLoginDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.UserRegisterDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.UserUpdateDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.config.CustomUserDetailsService;
import org.cst8319.niyitangajeanpierre.talentgearbackend.config.JwtAuthentication;
import org.cst8319.niyitangajeanpierre.talentgearbackend.config.JwtUtil;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.RoleEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.UserEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.RoleRepository;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;



@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationService {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    public String authenticateUser(@RequestBody UserLoginDto userLoginDto) {
        log.debug("Login username inside service method: {}", userLoginDto.getUsername());
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userLoginDto.getUsername());
        log.debug("user details inside service method: {}", userDetails.getUsername());


        if(!userLoginDto.getUsername().equals(userDetails.getUsername())) {
            throw new BadCredentialsException("Invalid username");
        }

        if (!bCryptPasswordEncoder.matches(userLoginDto.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        Authentication authentication = new JwtAuthentication(userDetails);
        log.debug("Authenticated user: {}", authentication.getPrincipal());

        try {



            String token = jwtUtil.generateToken(authentication);
            log.debug("Token inside service method: {}", token);
            return token;

        } catch (Exception e) {
            // Handle authentication failure
            log.error("Problem generating token", e);
            throw new BadCredentialsException("Authentication failed for user: " + userLoginDto.getUsername());
        }
    }


    public UserEntity createUser(@RequestBody UserRegisterDto userRegisterDto) {
        log.info("Register attempt user {}", userRegisterDto.getUsername());
        log.info("Register attempt user {}", userRegisterDto.getPassword());
        log.info("Register attempt user with roles:  {}", userRegisterDto.getRoles());

        // Check if username follow required format
        if (!isValidUsername(userRegisterDto.getUsername())) {
            log.debug("Invalid username. It should be at least three characters.");
            throw new BadCredentialsException("Invalid username. It should be at least three characters.");
        }

        // Check if username already exists in the database
        if (userRepository.existsByUsername(userRegisterDto.getUsername())) {
            log.debug("Username {} already exists. Try a different username.", userRegisterDto.getUsername());
            throw new IllegalArgumentException("Username already exists. Try a different username");
        }

        // Check if email follow required format
        if (!isValidEmail(userRegisterDto.getEmail())) {
            log.debug("Email {} does not follow the expected format", userRegisterDto.getEmail());
            throw new BadCredentialsException("Email {} does not follow the expected format");
        }

        // Check if password follow required format
        if (!isValidPassword(userRegisterDto.getPassword())) {
            log.warn("Password {} does not follow the expected format", userRegisterDto.getPassword());
            throw new BadCredentialsException("Password {} does not follow the expected format");
        }


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
        Set<RoleEntity> roles = new HashSet<>();
        for (String roleName: userRegisterDto.getRoles()){
            Optional<RoleEntity> optionalRoleEntity = roleRepository.findByName(roleName);
            if(optionalRoleEntity.isPresent()) {
                roles.add(optionalRoleEntity.get());
            } else {
                throw new IllegalArgumentException("Invalid role" + roleName);
            }
            newUser.setRoles(roles);
        }

        return userRepository.save(newUser);
    }

    public String registerAndAuthenticateUser(UserRegisterDto userRegisterDto) {
        log.info("Inside registerAndAuthenticateUser");
        // Register the user
        UserEntity newUser = createUser(userRegisterDto);

        // Create a UserLoginDto from the registration data
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername(newUser.getUsername());
        userLoginDto.setPassword(userRegisterDto.getPassword());

        // Use the existing authenticateUser method
        return authenticateUser(userLoginDto);
    }

    public UserEntity updateUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserUpdateDto userUpdateDto) {

        // Get the authenticated user's username
        String username = userDetails.getUsername();


        // Fetch the existing user
        UserEntity existingUser = userRepository.findByUsername(username);
        if(existingUser == null) {
            throw new RuntimeException("User not found");
        }

        // Update fields
        existingUser.setPhoneNumber(userUpdateDto.getPhoneNumber());
        existingUser.setAddress(userUpdateDto.getAddress());
        existingUser.setBio(userUpdateDto.getBio());
        existingUser.setWebsite(userUpdateDto.getWebsite());
        existingUser.setResumeUrl(userUpdateDto.getResumeUrl());

        // Validate if the roles exist in the database
        updateRoles(existingUser, userUpdateDto.getRoles());

        // Save and return the updated user
        return userRepository.save(existingUser);
    }

    // Method to Generate Password reset Token
    public String generatePasswordResetToken(String username){
        UserEntity user = userRepository.findByUsername(username);
        if(user == null) {
            throw new RuntimeException("User not found");
        }
        // Generate a random token
        String token = UUID.randomUUID().toString();

        // Store the token in the user entity
        user.setPasswordResetToken(token);
        user.setPasswordResetTokenExpirationDate(LocalDateTime.now().plusMinutes(30));
        userRepository.save(user);
        return token;
    }

    // Method to send the password reset email
    public void sendPasswordResetEmail(String username, String token) {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }

        String resetLink = "http://localhost:8080/reset-password?token=" + token + "&username=" + username;

        // Send the email using SendGrid
        emailService.sendSimpleMessage(user.getEmail(), "Password Reset Request", "Hello, To reset your password, please click on the following link: " + resetLink);
    }

    public void resetPassword(String token, String username, PasswordResetDto passwordResetDto) {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if (!user.getPasswordResetToken().equals(token) || user.getPasswordResetTokenExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invalid or expired token");
        }

        // Update the user's password
        if (isValidPassword(passwordResetDto.getNewPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(passwordResetDto.getNewPassword()));
            user.setPasswordResetToken(null);
            user.setPasswordResetTokenExpirationDate(null);
            userRepository.save(user);
        }
    }



    private void updateRoles(UserEntity existingUser, Set<String> roles) {
        Set<RoleEntity> roleEntities = new HashSet<>();
        for (String roleName: roles) {
            Optional<RoleEntity> optionalRoleEntity = roleRepository.findByName(roleName);
            if (optionalRoleEntity.isPresent()) {
                roleEntities.add(optionalRoleEntity.get());
            } else {
                throw new IllegalArgumentException("Invalid role" + roleName);
            }
        }
        existingUser.setRoles(roleEntities);
    }



    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
    }

    public boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.find();
    }

    private boolean isValidUsername(String username) {
        String usernameRegex = "^[a-zA-Z0-9_]{3,20}$";
        Pattern usernamePattern = Pattern.compile(usernameRegex);
        Matcher matcher = usernamePattern.matcher(username);
        return matcher.find();
    }


}
