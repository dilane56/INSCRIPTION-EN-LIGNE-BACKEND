package org.kfokam48.inscriptionenlignebackend.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    @NotNull(message = "Password is required")
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @NotNull(message = "Email is required")
    @NotBlank(message = "Email cannot be blank")
    private String email;
}
