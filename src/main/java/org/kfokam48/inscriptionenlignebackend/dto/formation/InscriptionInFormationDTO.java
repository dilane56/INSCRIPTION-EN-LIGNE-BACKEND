package org.kfokam48.inscriptionenlignebackend.dto.formation;

import lombok.Data;

@Data
public class InscriptionInFormationDTO {
    private Long id;
    private String candidatEmail;
    private String anneeAcademique;
    private String dateSoumission;

}
