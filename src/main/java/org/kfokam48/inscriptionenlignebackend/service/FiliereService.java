package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.filiere.FiliereRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.filiere.FiliereResponseDTO;

import java.util.List;

public interface FiliereService {
    public List<FiliereResponseDTO> getAllAneeAcademique();
    public FiliereResponseDTO getAnneeById(Long id);
    public FiliereResponseDTO createAnne(FiliereRequestDTO filiereRequestDTO);
    public FiliereResponseDTO updateAnnee(FiliereRequestDTO filiereRequestDTO, Long id);
    public String deleteAnnee(Long id);
}
