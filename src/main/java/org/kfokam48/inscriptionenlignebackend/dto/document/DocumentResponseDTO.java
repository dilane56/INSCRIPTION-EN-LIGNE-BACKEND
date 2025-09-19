package org.kfokam48.inscriptionenlignebackend.dto.document;

import lombok.Data;
import org.kfokam48.inscriptionenlignebackend.enums.TypeDocument;

import java.time.LocalDateTime;

@Data
public class DocumentResponseDTO {
    private Long id;
    private String nom;
    private String nomOriginal;
    private TypeDocument typeDocument;
    private String mimeType;
    private Long taille;
    private Boolean valide;
    private String commentaireValidation;
    private LocalDateTime dateUpload;
    private LocalDateTime dateValidation;
    private String adminValidateurNom;
    private Long inscriptionId;
}
