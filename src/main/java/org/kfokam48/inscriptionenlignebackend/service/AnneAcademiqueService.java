package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.admin.AdminRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.admin.AdminResponseDTO;
import org.kfokam48.inscriptionenlignebackend.dto.anneAcademique.AnneeAcademiqueRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.anneAcademique.AnneeAcademiqueResponseDTO;

import java.util.List;

public interface AnneAcademiqueService {
    public List<AnneeAcademiqueResponseDTO> getAllAneeAcademique();
    public AnneeAcademiqueResponseDTO getAnneeById(Long id);
    public AnneeAcademiqueResponseDTO createAnne(AnneeAcademiqueRequestDTO anneeAcademiqueRequestDTO);
    public AnneeAcademiqueResponseDTO updateAnnee(AnneeAcademiqueRequestDTO anneeAcademiqueRequestDTO, Long id);
    public String deleteAnnee(Long id);
}
