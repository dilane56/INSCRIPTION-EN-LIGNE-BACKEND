package org.kfokam48.inscriptionenlignebackend.service.impl;

import org.kfokam48.inscriptionenlignebackend.dto.parcours.ParcoursAcademiqueDTO;
import org.kfokam48.inscriptionenlignebackend.mapper.ParcoursAcademiqueMapper;
import org.kfokam48.inscriptionenlignebackend.model.Candidat;
import org.kfokam48.inscriptionenlignebackend.model.ParcoursAcademique;
import org.kfokam48.inscriptionenlignebackend.repository.CandidatRepository;
import org.kfokam48.inscriptionenlignebackend.repository.ParcoursAcademiqueRepository;
import org.kfokam48.inscriptionenlignebackend.service.ParcoursAcademiqueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ParcoursAcademiqueServiceImpl implements ParcoursAcademiqueService {
    
    private final ParcoursAcademiqueRepository parcoursRepository;
    private final ParcoursAcademiqueMapper parcoursMapper;
    private final CandidatRepository candidatRepository;
    
    public ParcoursAcademiqueServiceImpl(ParcoursAcademiqueRepository parcoursRepository,
                                        ParcoursAcademiqueMapper parcoursMapper,
                                        CandidatRepository candidatRepository) {
        this.parcoursRepository = parcoursRepository;
        this.parcoursMapper = parcoursMapper;
        this.candidatRepository = candidatRepository;
    }

    @Override
    public ParcoursAcademiqueDTO save(ParcoursAcademiqueDTO dto) {
        ParcoursAcademique parcours = parcoursMapper.toEntity(dto);
        Candidat candidat = candidatRepository.findById(dto.getCandidatId()).orElseThrow();
        parcours.setCandidat(candidat);
        parcours = parcoursRepository.save(parcours);
        return parcoursMapper.toDTO(parcours);
    }

    @Override
    public List<ParcoursAcademiqueDTO> getParcoursCandidat(Long candidatId) {
        List<ParcoursAcademique> parcours = parcoursRepository.findByCandidatIdOrderByDateDebutDesc(candidatId);
        return parcoursMapper.toDTOList(parcours);
    }

    @Override
    public void delete(Long id) {
        parcoursRepository.deleteById(id);
    }
}