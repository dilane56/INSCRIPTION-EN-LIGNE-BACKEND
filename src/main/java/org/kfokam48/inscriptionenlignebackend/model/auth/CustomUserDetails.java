package org.kfokam48.inscriptionenlignebackend.model.auth;

import org.kfokam48.inscriptionenlignebackend.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomUserDetails implements UserDetails, OAuth2User {

    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;
    private final User user;

    // Constructeur pour authentification classique
    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.attributes = null;
        this.user = null;
    }
    
    // Constructeur pour OAuth2
    public CustomUserDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.username = user.getEmail();
        this.password = null;
        this.authorities = java.util.List.of(() -> "ROLE_" + user.getRole().name());
        this.attributes = attributes;
    }
    
    // Constructeur simplifié pour OAuth2 (sans attributes)
    public CustomUserDetails(User user) {
        this.user = user;
        this.username = user.getEmail();
        this.password = null;
        this.authorities = java.util.List.of(() -> "ROLE_" + user.getRole().name());
        this.attributes = java.util.Map.of(
            "sub", user.getProviderId() != null ? user.getProviderId() : user.getId().toString(),
            "email", user.getEmail(),
            "given_name", user.getFirstName(),
            "family_name", user.getLastName()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Supposons que le compte n'expire jamais.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Supposons que le compte n'est jamais verrouillé.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Supposons que les informations d'identification n'expirent jamais.
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    // Méthodes OAuth2User
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    
    @Override
    public String getName() {
        return username;
    }
    
    public User getUser() {
        return user;
    }
}