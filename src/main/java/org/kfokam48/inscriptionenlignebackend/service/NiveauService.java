package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.niveau.NiveauRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.niveau.NiveauResponseDTO;

import java.util.List;

public interface NiveauService {
    NiveauResponseDTO createNiveau(NiveauRequestDTO dto);
    NiveauResponseDTO updateNiveau(Long id, NiveauRequestDTO dto);
    List<NiveauResponseDTO> getAllNiveaux();
    String deleteNiveau(Long id);
}

