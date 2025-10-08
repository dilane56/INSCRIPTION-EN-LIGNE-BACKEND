package org.kfokam48.inscriptionenlignebackend.dto.user;

import lombok.Data;
import org.kfokam48.inscriptionenlignebackend.enums.Roles;

@Data
public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Roles role;

}
