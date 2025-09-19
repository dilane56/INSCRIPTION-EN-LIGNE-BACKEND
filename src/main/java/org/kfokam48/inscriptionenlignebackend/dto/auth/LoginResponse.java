package org.kfokam48.inscriptionenlignebackend.dto.auth;

import lombok.Data;
import org.kfokam48.inscriptionenlignebackend.dto.user.UserResponseDTO;


@Data
public class LoginResponse {
    private String token;
   // private String role;
    private UserResponseDTO user;
    // + Getters/Setters
}
