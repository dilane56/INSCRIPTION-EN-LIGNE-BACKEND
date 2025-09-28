package org.kfokam48.inscriptionenlignebackend.dto.formation;

import lombok.Data;

import java.util.List;

@Data
public class FormationResponseDTO {
    private Long id;
    private String nomFormation;
    private String etablissement;
    private String description;
    private Integer duree;
    private Double prix;
    private String prerequis;
    private FiliereDTO filiere;
    private List<InscriptionInFormationDTO> inscriptions;
    
    @Data
    public static class FiliereDTO {
        private Long id;
        private String nomFiliere;
    }

}
