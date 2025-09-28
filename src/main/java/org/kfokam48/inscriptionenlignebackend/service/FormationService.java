package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.formation.FormationRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.formation.FormationResponseDTO;

import java.util.List;

public interface FormationService {
    public FormationResponseDTO createFormation(FormationRequestDTO formationRequestDTO);
    public FormationResponseDTO updateFormation(Long id, FormationRequestDTO formationRequestDTO);
    public void deleteFormation(Long id);
    public FormationResponseDTO getFormationById(Long id);
    public List<FormationResponseDTO> getAllFormations();
}
