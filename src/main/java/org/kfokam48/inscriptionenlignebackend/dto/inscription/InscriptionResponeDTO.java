package org.kfokam48.inscriptionenlignebackend.dto.inscription;

import lombok.Data;

import java.util.List;

@Data
public class InscriptionResponeDTO {
    private Long id;
    private String candidatEmail;
    private String formationName;
    private String anneeAcademique;
    private String dateSoumission;
    private String etat;
    private List<DocumentInInscription> documents;

}
