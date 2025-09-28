package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kfokam48.inscriptionenlignebackend.dto.formation.FormationRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.formation.FormationResponseDTO;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceNotFoundException;
import org.kfokam48.inscriptionenlignebackend.mapper.FormationMapper;
import org.kfokam48.inscriptionenlignebackend.model.Formation;
import org.kfokam48.inscriptionenlignebackend.model.Filiere;
import org.kfokam48.inscriptionenlignebackend.repository.FormationRepository;
import org.kfokam48.inscriptionenlignebackend.repository.FiliereRepository;
import org.kfokam48.inscriptionenlignebackend.service.FormationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FormationServiceImpl  implements FormationService {
    private final FormationRepository formationRepository;
    private final FiliereRepository filiereRepository;
    private final FormationMapper formationMapper;


    @Override
    public FormationResponseDTO createFormation(FormationRequestDTO formationRequestDTO) {
        Formation formation = formationMapper.formationRequestDTOToFormation(formationRequestDTO);
        formation = formationRepository.save(formation);
        return formationMapper.formationToFormationResponseDTO(formation);
    }

    @Override
    public FormationResponseDTO updateFormation(Long id, FormationRequestDTO formationRequestDTO) {
        Formation formation = formationRepository.findById(id).orElseThrow(()->new RessourceNotFoundException("Formation not found"));
        
        formation.setNomFormation(formationRequestDTO.getNomFormation());
        formation.setEtablissement(formationRequestDTO.getEtablissement());
        formation.setDescription(formationRequestDTO.getDescription());
        formation.setDuree(formationRequestDTO.getDuree());
        formation.setPrix(formationRequestDTO.getPrix());
        formation.setPrerequis(formationRequestDTO.getPrerequis());
        
        if (formationRequestDTO.getFiliereId() != null) {
            Filiere filiere = filiereRepository.findById(formationRequestDTO.getFiliereId())
                    .orElseThrow(() -> new RessourceNotFoundException("Filiere not found"));
            formation.setFiliere(filiere);
        }
        
        formation = formationRepository.save(formation);
        return formationMapper.formationToFormationResponseDTO(formation);
    }

    @Override
    public void deleteFormation(Long id) {
        Formation formation = formationRepository.findById(id).orElseThrow(()->new RessourceNotFoundException("Formation not found"));
        formationRepository.delete(formation);
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
