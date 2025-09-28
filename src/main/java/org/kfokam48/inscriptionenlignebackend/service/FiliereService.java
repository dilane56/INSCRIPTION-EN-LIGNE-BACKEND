package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.filiere.FiliereDTO;
import org.kfokam48.inscriptionenlignebackend.dto.filiere.FiliereRequestDTO;

import java.util.List;

public interface FiliereService {
    List<FiliereDTO> getAll();
    FiliereDTO create(FiliereRequestDTO request);
    FiliereDTO update(Long id, FiliereRequestDTO request);
    void delete(Long id);
}