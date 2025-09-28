package org.kfokam48.inscriptionenlignebackend.dto.candidat;

import lombok.Data;

@Data
public class CoordonneesDTO {
    private String adresse;
    private String ville;
    private String codePostal;
    private String pays;
    private String contactPourUrgence;
    private String telephoneUrgence;
}