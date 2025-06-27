package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.transaction.Transactional;
import org.kfokam48.inscriptionenlignebackend.dto.inscription.InscriptionRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.inscription.InscriptionResponeDTO;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceNotFoundException;
import org.kfokam48.inscriptionenlignebackend.mapper.InscriptionMapper;
import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.kfokam48.inscriptionenlignebackend.repository.FiliereRepository;
import org.kfokam48.inscriptionenlignebackend.repository.CandidatRepository;
import org.kfokam48.inscriptionenlignebackend.repository.FormationRepository;
import org.kfokam48.inscriptionenlignebackend.repository.InscriptionRepository;
import org.kfokam48.inscriptionenlignebackend.service.InscriptionService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class InscriptionServiceImpl  implements InscriptionService {
    private final InscriptionRepository inscriptionRepository;
    private final InscriptionMapper inscriptionMapper;
    private final CandidatRepository candidatRepository;
    private final FiliereRepository academiqueRepository;
    private final FormationRepository formationRepository;

    public InscriptionServiceImpl(InscriptionRepository inscriptionRepository, InscriptionMapper inscriptionMapper, CandidatRepository candidatRepository, FiliereRepository academiqueRepository, FormationRepository formationRepository) {
        this.inscriptionRepository = inscriptionRepository;
        this.inscriptionMapper = inscriptionMapper;
        this.candidatRepository = candidatRepository;
        this.academiqueRepository = academiqueRepository;
        this.formationRepository = formationRepository;
    }

    @Override
    public InscriptionResponeDTO findById(Long id) {
        Inscription inscription = inscriptionRepository.findById(id).orElseThrow(()->new RessourceNotFoundException("Inscription Not Found"));
        return inscriptionMapper.inscriptionToInscriptionResponeDTO(inscription);
    }

    @Override
    public InscriptionResponeDTO save(InscriptionRequestDTO inscriptionRequestDTO) {
        Inscription inscription = inscriptionMapper.inscriptionRequestDTOToInscription(inscriptionRequestDTO);
        inscription = inscriptionRepository.save(inscription);
        return inscriptionMapper.inscriptionToInscriptionResponeDTO(inscription);
    }

    @Override
    public InscriptionResponeDTO update(InscriptionRequestDTO inscriptionRequestDTO, Long id) {
        Inscription inscription = inscriptionRepository.findById(id).orElseThrow(()->new RessourceNotFoundException("Inscription Not Found"));
        inscription.setFormation(formationRepository.findById(inscriptionRequestDTO.getFormationId()).orElseThrow(()->new RessourceNotFoundException("Formation Not Found")));
        inscription.setCandidat(candidatRepository.findById(inscriptionRequestDTO.getCandidatId()).orElseThrow(()->new RessourceNotFoundException("Candidat Not Found")));
        inscription.setAnneeAcademique(academiqueRepository.findById(inscriptionRequestDTO.getAnneeAcademiqueId()).orElseThrow(()->new RessourceNotFoundException("Annee Academic Not Found")));
        inscription = inscriptionRepository.save(inscription);
        return inscriptionMapper.inscriptionToInscriptionResponeDTO(inscription);
    }

    @Override
    public String delete(Long id) {
        Inscription inscription = inscriptionRepository.findById(id).orElseThrow(()->new RessourceNotFoundException("Inscription Not Found"));
        inscriptionRepository.delete(inscription);
        return "Inscription Deleted Successfully";
    }

    @Override
    public List<InscriptionResponeDTO> findAll() {
        return inscriptionMapper.inscriptionListToInscriptionResponseDTOList(inscriptionRepository.findAll());
    }
}
