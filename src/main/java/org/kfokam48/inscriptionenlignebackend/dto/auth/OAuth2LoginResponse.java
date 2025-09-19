package org.kfokam48.inscriptionenlignebackend.dto.auth;

import lombok.Data;

@Data
public class OAuth2LoginResponse {
    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private String provider;
    private String imageUrl;
    private boolean isNewUser;
}