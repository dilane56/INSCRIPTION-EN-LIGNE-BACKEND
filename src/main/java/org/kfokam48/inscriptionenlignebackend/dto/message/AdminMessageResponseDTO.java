package org.kfokam48.inscriptionenlignebackend.dto.message;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminMessageResponseDTO {
    private Long id;
    private Long candidatId;
    private String candidatNom;
    private String candidatPrenom;
    private String candidatEmail;
    private String objet;
    private String contenu;
    private String typeMessage;
    private String statut;
    private Boolean envoyeParAdmin;
    private LocalDateTime dateEnvoi;
    private LocalDateTime dateLecture;
}