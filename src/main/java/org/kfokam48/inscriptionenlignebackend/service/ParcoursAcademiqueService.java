package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.parcours.ParcoursAcademiqueDTO;

import java.util.List;

public interface ParcoursAcademiqueService {
    ParcoursAcademiqueDTO save(ParcoursAcademiqueDTO dto);
    List<ParcoursAcademiqueDTO> getParcoursCandidat(Long candidatId);
    void delete(Long id);
}