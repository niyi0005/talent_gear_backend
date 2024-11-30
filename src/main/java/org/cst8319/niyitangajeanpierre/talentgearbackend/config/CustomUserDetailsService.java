package org.cst8319.niyitangajeanpierre.talentgearbackend.config;

import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.UserEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from database by username
        UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                getAuthorities(userEntity) // Set authorities/roles
        );
    }

    // Example method to get authorities, assuming the user entity has roles or permissions
    private Collection<SimpleGrantedAuthority> getAuthorities(UserEntity userEntity) {

        // Convert the Role enum to a "ROLE_" prefixed string for Spring Security
        String roleName = "ROLE_" + userEntity.getRole().name(); // Convert to "ROLE_JOB_SEEKER" or "ROLE_EMPLOYER"

        // Return a collection with the SimpleGrantedAuthority (role) for Spring Security
        return Collections.singletonList(new SimpleGrantedAuthority(roleName));
    }
}
