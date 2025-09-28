package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.etablissement.EtablissementDTO;
import org.kfokam48.inscriptionenlignebackend.dto.etablissement.EtablissementRequestDTO;

import java.util.List;

public interface EtablissementService {
    List<EtablissementDTO> getAllActifs();
    List<EtablissementDTO> getAll();
    EtablissementDTO create(EtablissementRequestDTO request);
    EtablissementDTO update(Long id, EtablissementRequestDTO request);
    void delete(Long id);
}