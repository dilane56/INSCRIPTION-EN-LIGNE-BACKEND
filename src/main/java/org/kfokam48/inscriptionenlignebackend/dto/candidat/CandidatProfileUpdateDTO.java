package org.kfokam48.inscriptionenlignebackend.dto.candidat;

import lombok.Data;
import org.kfokam48.inscriptionenlignebackend.enums.Sexe;

import java.time.LocalDate;

@Data
public class CandidatProfileUpdateDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate dateNaissance;
    private String nationalite;
    private String typeDePieceIdentite;
    private String numeroPieceIdentite;
    private String adresse;
    private String ville;
    private String codePostal;
    private String pays;
    private String contactPourUrgence;
    private String telephoneUrgence;
    private Sexe sexe;
}