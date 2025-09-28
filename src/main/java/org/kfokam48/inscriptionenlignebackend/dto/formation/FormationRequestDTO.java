package org.kfokam48.inscriptionenlignebackend.dto.formation;

import lombok.Data;

@Data
public class FormationRequestDTO {
    private String nomFormation;
    private String etablissement;
    private String description;
    private Integer duree;
    private Double prix;
    private String prerequis;
    private Long filiereId;
}
