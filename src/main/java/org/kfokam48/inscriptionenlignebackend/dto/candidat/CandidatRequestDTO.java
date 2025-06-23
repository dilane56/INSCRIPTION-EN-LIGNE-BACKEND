package org.kfokam48.inscriptionenlignebackend.dto.candidat;

import jakarta.persistence.Column;
import lombok.Data;
import org.kfokam48.inscriptionenlignebackend.dto.user.UserRequestDTO;
import org.kfokam48.inscriptionenlignebackend.enums.Sexe;

import java.time.LocalDate;



@Data
public class CandidatRequestDTO {
    private LocalDate dateNaissance;
    private String firstName;
    private String lastName;
    private String password;
    private Sexe sexe;
    private String nationalite;
    private String typeDePieceIdentite;




}
