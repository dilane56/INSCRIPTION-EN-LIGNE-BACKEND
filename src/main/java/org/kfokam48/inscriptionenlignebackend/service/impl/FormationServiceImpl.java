package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.transaction.Transactional;
import org.kfokam48.inscriptionenlignebackend.dto.formation.FormationRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.formation.FormationResponseDTO;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceNotFoundException;
import org.kfokam48.inscriptionenlignebackend.mapper.FormationMapper;
import org.kfokam48.inscriptionenlignebackend.model.Formation;
import org.kfokam48.inscriptionenlignebackend.repository.FormationRepository;
import org.kfokam48.inscriptionenlignebackend.repository.NiveauRepository;
import org.kfokam48.inscriptionenlignebackend.service.FormationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FormationServiceImpl  implements FormationService {
    private final FormationRepository formationRepository;
    private final FormationMapper formationMapper;
    private final NiveauRepository niveauRepository;

    public FormationServiceImpl(FormationRepository formationRepository, FormationMapper formationMapper, NiveauRepository niveauRepository) {
        this.formationRepository = formationRepository;
        this.formationMapper = formationMapper;
        this.niveauRepository = niveauRepository;
    }

    @Override
    public FormationResponseDTO createFormation(FormationRequestDTO formationRequestDTO) {
        Formation formation = formationMapper.formationRequestDTOToFormation(formationRequestDTO);
        formation = formationRepository.save(formation);
        return formationMapper.formationToFormationResponseDTO(formation);
    }

    @Override
    public FormationResponseDTO updateFormation(FormationRequestDTO formationRequestDTO, Long id) {
        Formation formation = formationRepository.findById(id).orElseThrow(()->new RessourceNotFoundException("Formation not found"));
        formation.setNomFormation(formationRequestDTO.getNomFormation());
        formation.setEtablissement(formationRequestDTO.getEtablissement());
        formation.setSpecialite(formationRequestDTO.getSpecialite());
        formation.setNiveau(niveauRepository.findById(formationRequestDTO.getNiveauId()).orElseThrow(()->new RessourceNotFoundException("Niveau not found")));
        formation = formationRepository.save(formation);
        return formationMapper.formationToFormationResponseDTO(formation);
    }

    @Override
    public String deleteFormation(Long id) {
        Formation formation = formationRepository.findById(id).orElseThrow(()->new RessourceNotFoundException("Formation not found"));
        formationRepository.delete(formation);
        return "Formation deleted Successfully";
    }

    @Override
    public FormationResponseDTO getFormationById(Long id) {
        Formation formation = formationRepository.findById(id).orElseThrow(()->new RessourceNotFoundException("Formation not found"));
        return formationMapper.formationToFormationResponseDTO(formation);
    }

    @Override
    public List<FormationResponseDTO> getAllFormations() {
        return formationMapper.formationListToFormationResponseDTOList(formationRepository.findAll());
    }
}
