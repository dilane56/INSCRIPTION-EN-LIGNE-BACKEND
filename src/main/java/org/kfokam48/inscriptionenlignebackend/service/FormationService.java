package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.formation.FormationRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.formation.FormationResponseDTO;

import java.util.List;

public interface FormationService {
    public FormationResponseDTO createFormation(FormationRequestDTO formationRequestDTO);
    public FormationResponseDTO updateFormation(FormationRequestDTO formationRequestDTO,Long id);
    public String deleteFormation(Long id);
    public FormationResponseDTO getFormationById(Long id);
    public List<FormationResponseDTO> getAllFormations();
}
