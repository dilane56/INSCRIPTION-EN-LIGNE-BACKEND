package org.kfokam48.inscriptionenlignebackend.dto.formation;

import lombok.Data;
import org.kfokam48.inscriptionenlignebackend.dto.niveau.NiveauInFromationDTO;

import java.util.List;

@Data
public class FormationResponseDTO {
    private Long id;
    private String nomFormation;
    private String etablissement;
    private String specialite;
    private NiveauInFromationDTO niveau;
    private List<InscriptionInFormationDTO> inscriptions;

}
