package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.transaction.Transactional;
import org.kfokam48.inscriptionenlignebackend.dto.anneAcademique.AnneeAcademiqueRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.anneAcademique.AnneeAcademiqueResponseDTO;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceNotFoundException;
import org.kfokam48.inscriptionenlignebackend.mapper.AnneeAcademiqueMapper;
import org.kfokam48.inscriptionenlignebackend.model.AnneeAcademique;
import org.kfokam48.inscriptionenlignebackend.repository.AnneeAcademiqueRepository;
import org.kfokam48.inscriptionenlignebackend.service.AnneAcademiqueService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class AnneAcademiqueServiceImpl implements AnneAcademiqueService {
    private final AnneeAcademiqueRepository anneeAcademiqueRepository;
    private final AnneeAcademiqueMapper anneeAcademiqueMapper;

    public AnneAcademiqueServiceImpl(AnneeAcademiqueRepository anneeAcademiqueRepository, AnneeAcademiqueMapper anneeAcademiqueMapper) {
        this.anneeAcademiqueRepository = anneeAcademiqueRepository;
        this.anneeAcademiqueMapper = anneeAcademiqueMapper;
    }

    @Override
    public List<AnneeAcademiqueResponseDTO> getAllAneeAcademique() {
        return anneeAcademiqueMapper.anneeAcademiqueListToAnneeAcademiqueResponseDTOList(anneeAcademiqueRepository.findAll());
    }

    @Override
    public AnneeAcademiqueResponseDTO getAnneeById(Long id) {
        AnneeAcademique anneeAcademique = anneeAcademiqueRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("AnneeAcademique not found"));
        return anneeAcademiqueMapper.anneeAcademiqueToAnneeAcademiqueResponseDTO(anneeAcademique);
    }

    @Override
    public AnneeAcademiqueResponseDTO createAnne(AnneeAcademiqueRequestDTO anneeAcademiqueRequestDTO) {
        AnneeAcademique anneeAcademique = anneeAcademiqueMapper.anneeAcademiqueRequestDTOToAnneeAcademique(anneeAcademiqueRequestDTO);
        return anneeAcademiqueMapper.anneeAcademiqueToAnneeAcademiqueResponseDTO(anneeAcademiqueRepository.save(anneeAcademique));
    }

    @Override
    public AnneeAcademiqueResponseDTO updateAnnee(AnneeAcademiqueRequestDTO anneeAcademiqueRequestDTO, Long id) {
        AnneeAcademique anneeAcademique = anneeAcademiqueRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("AnneeAcademique not found"));
        anneeAcademique.setLibelle(anneeAcademiqueRequestDTO.getLibelle());
        return anneeAcademiqueMapper.anneeAcademiqueToAnneeAcademiqueResponseDTO(anneeAcademiqueRepository.save(anneeAcademique));
    }

    @Override
    public String deleteAnnee(Long id) {
        AnneeAcademique anneeAcademique = anneeAcademiqueRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("AnneeAcademique not found"));
        anneeAcademiqueRepository.delete(anneeAcademique);
        return "AnneeAcademique deleted successfully";
    }
}
