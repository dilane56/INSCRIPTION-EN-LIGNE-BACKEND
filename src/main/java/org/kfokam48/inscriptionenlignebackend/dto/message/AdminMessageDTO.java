package org.kfokam48.inscriptionenlignebackend.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminMessageDTO {
    
    @NotNull(message = "Candidat ID ne peut pas être null")
    private Long candidatId;
    
    @NotBlank(message = "L'objet ne peut pas être vide")
    private String objet;
    
    @NotBlank(message = "Le contenu ne peut pas être vide")
    private String contenu;
}