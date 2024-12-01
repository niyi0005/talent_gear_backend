package org.cst8319.niyitangajeanpierre.talentgearbackend.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {


    private final Object principal;
    private final Object credentials;
    private  Object details;

    // Constructor accepting principal, credentials, and authorities
    public CustomAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true);

    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public void setDetails(Object details) {
        this.details = details;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return null; // You can set authorities if needed
    }


}
