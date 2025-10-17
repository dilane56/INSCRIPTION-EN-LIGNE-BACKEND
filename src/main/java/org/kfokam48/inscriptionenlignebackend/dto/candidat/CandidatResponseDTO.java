package org.kfokam48.inscriptionenlignebackend.dto.candidat;

import lombok.Data;
import org.kfokam48.inscriptionenlignebackend.dto.user.UserResponseDTO;
import org.kfokam48.inscriptionenlignebackend.enums.Sexe;

import java.time.LocalDate;
import java.util.List;

@Data
public class CandidatResponseDTO extends UserResponseDTO {
    private LocalDate dateNaissance;
    private List<InscriptionInCandidatDTO> inscriptions;
    private String nationalite;
    private Sexe sexe;
    private String adresse;
    private String telephone;
    private String contactPourUrgence;
    private String telephoneUrgence;
    private String ville;
    private String codePostal;
    private String pays;

}
