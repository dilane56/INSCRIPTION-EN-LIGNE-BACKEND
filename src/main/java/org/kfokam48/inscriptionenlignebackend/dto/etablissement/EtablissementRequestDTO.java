package org.kfokam48.inscriptionenlignebackend.dto.etablissement;

import lombok.Data;

@Data
public class EtablissementRequestDTO {
    private String nom;
    private String ville;
    private String pays;
    private String type;
    private Boolean actif = true;
}