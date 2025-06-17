package org.kfokam48.inscriptionenlignebackend.dto.formation;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InscriptionInFormationDTO {
    private Long id;
    private String candidatEmail;
    private String anneeAcademique;
    private LocalDate dateSoumission;

}
