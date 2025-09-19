package org.kfokam48.inscriptionenlignebackend.dto.workflow;

import lombok.Data;
import org.kfokam48.inscriptionenlignebackend.enums.StatutInscription;

@Data
public class WorkflowActionDTO {
    private Long inscriptionId;
    private StatutInscription nouveauStatut;
    private String commentaire;
    private Long adminId;
}