package org.cst8319.niyitangajeanpierre.talentgearbackend.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j

@RequiredArgsConstructor
public class JwtAuthentication implements Authentication {

    private final UserDetails userDetails;
    private final boolean authenticated=true;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    public List<String> getAuthoritiesAsList() {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (this.authenticated && !isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor instead.");
        }
        log.warn("Attempt to change authentication status ignored");

    }

    @Override
    public String getName() {
        return userDetails.getUsername();
    }
}
