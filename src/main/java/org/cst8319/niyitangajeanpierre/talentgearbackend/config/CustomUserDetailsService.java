package org.cst8319.niyitangajeanpierre.talentgearbackend.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.RoleEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.UserEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from database by username
        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            log.warn("User not found with username: {}", username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        log.info("User found: {} ", user);

        // Initialize roles to avoid NullPointerException
        Set<RoleEntity> roles = user.getRoles();
        if (roles == null) {
            roles = new HashSet<>();
        }

        // Fetch roles from database if not already loaded
        if (roles.isEmpty()) {
            user = userRepository.findById(user.getId()).orElseThrow();
            roles = user.getRoles();
        }

        if (roles.isEmpty()) {
            log.warn("Roles are empty for user: " + username);
        } else {
            log.info("Roles for user: " + username + " are: " + roles);
        }

        // Convert user roles to granted authorities
        Set<GrantedAuthority> authorities = getAuthorities(roles);
        log.info("Authorities: " + authorities);
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    // Method to get authorities (roles in our case)
    private Set<GrantedAuthority> getAuthorities(Set<RoleEntity> roles) {
        log.info("Retrieved roles in helper method: " + roles);
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());
    }

    // New method to create JwtAuthentication
    public Authentication createJwtAuthentication(String username) {
        UserDetails userDetails = loadUserByUsername(username);
        return new JwtAuthentication(userDetails);
    }
}
