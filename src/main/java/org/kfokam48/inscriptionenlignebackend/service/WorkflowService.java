package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.workflow.WorkflowActionDTO;
import org.kfokam48.inscriptionenlignebackend.enums.StatutInscription;

public interface WorkflowService {
    void changerStatut(Long inscriptionId, StatutInscription nouveauStatut, String commentaire, Long adminId);
    void soumettreInscription(Long inscriptionId);
    void validerInscription(Long inscriptionId, Long adminId, String commentaire);
    void rejeterInscription(Long inscriptionId, Long adminId, String commentaire);
}