package org.kfokam48.inscriptionenlignebackend.dto.candidat;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CandidatBasicInfoDTO {
    private String firstName;
    private String lastName;
    private LocalDate dateNaissance;
    private String nationalite;
    private String sexe;
}