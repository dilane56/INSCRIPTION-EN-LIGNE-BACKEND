package org.kfokam48.inscriptionenlignebackend.dto.candidat;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InscriptionInCandidatDTO {
    private Long id;
    private String formationName;
    private String anneeAcademique;
    private LocalDate dateSoumission;
    private String etat;

}
