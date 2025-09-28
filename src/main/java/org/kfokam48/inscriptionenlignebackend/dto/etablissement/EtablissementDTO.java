package org.kfokam48.inscriptionenlignebackend.dto.etablissement;

import lombok.Data;

@Data
public class EtablissementDTO {
    private Long id;
    private String nom;
    private String ville;
    private String pays;
    private String type;
}