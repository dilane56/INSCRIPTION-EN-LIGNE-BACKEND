package org.kfokam48.inscriptionenlignebackend.dto.candidat;

import lombok.Data;
import org.kfokam48.inscriptionenlignebackend.enums.StatutInscription;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class InscriptionInCandidatDTO {
    private Long id;
    private String formationName;
    private String anneeAcademique;
    private LocalDateTime dateSoumission;
    private StatutInscription statut;
    private LocalDateTime dateValidation;
    private String commentaireAdmin;
    private Integer etapeActuelle;
    private Double pourcentageCompletion;
    private String adminValidateurNom;
    private LocalDateTime derniereModification;

}
