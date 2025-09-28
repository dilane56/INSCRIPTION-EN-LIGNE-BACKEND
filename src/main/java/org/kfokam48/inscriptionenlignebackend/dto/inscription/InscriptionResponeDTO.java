package org.kfokam48.inscriptionenlignebackend.dto.inscription;

import lombok.Data;
import org.kfokam48.inscriptionenlignebackend.enums.StatutInscription;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InscriptionResponeDTO {
    private Long id;
    private String candidatEmail;
    private String candidatNom;
    private String candidatPrenom;
    private String formationName;
    private Long formationId;
    private String anneeAcademique;
    private Long anneeAcademiqueId;
    private LocalDateTime dateCreation;
    private LocalDateTime dateSoumission;
    private LocalDateTime dateValidation;
    private StatutInscription statut;
    private String commentaireAdmin;
    private Integer etapeActuelle;
    private Double pourcentageCompletion;
    private String adminValidateurNom;
    private List<DocumentInInscription> documents;
    private LocalDateTime derniereModification;

    // Coordonnées du candidat (exposées pour affichage côté frontend)
    private String adresse;
    private String ville;
    private String codePostal;
    private String pays;
    private String contactPourUrgence;
    private String telephoneUrgence;
}
