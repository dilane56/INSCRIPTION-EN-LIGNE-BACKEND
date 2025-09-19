package org.kfokam48.inscriptionenlignebackend.service.impl;

import org.kfokam48.inscriptionenlignebackend.dto.etape.EtapeInscriptionDTO;
import org.kfokam48.inscriptionenlignebackend.model.EtapeInscription;
import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.kfokam48.inscriptionenlignebackend.repository.EtapeInscriptionRepository;
import org.kfokam48.inscriptionenlignebackend.repository.InscriptionRepository;
import org.kfokam48.inscriptionenlignebackend.service.EtapeInscriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EtapeInscriptionServiceImpl implements EtapeInscriptionService {
    
    private final EtapeInscriptionRepository etapeRepository;
    private final InscriptionRepository inscriptionRepository;
    
    public EtapeInscriptionServiceImpl(EtapeInscriptionRepository etapeRepository, InscriptionRepository inscriptionRepository) {
        this.etapeRepository = etapeRepository;
        this.inscriptionRepository = inscriptionRepository;
    }

    @Override
    public List<EtapeInscriptionDTO> getEtapesByInscription(Long inscriptionId) {
        return etapeRepository.findByInscriptionIdOrderByNumeroEtape(inscriptionId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EtapeInscriptionDTO completerEtape(Long inscriptionId, Integer numeroEtape) {
        List<EtapeInscription> etapes = etapeRepository.findByInscriptionIdOrderByNumeroEtape(inscriptionId);
        
        EtapeInscription etape = etapes.stream()
                .filter(e -> e.getNumeroEtape().equals(numeroEtape))
                .findFirst()
                .orElseThrow();
                
        etape.setCompletee(true);
        etape.setDateCompletion(LocalDateTime.now());
        etapeRepository.save(etape);
        
        // Mettre à jour le pourcentage de completion
        Inscription inscription = inscriptionRepository.findById(inscriptionId).orElseThrow();
        inscription.setPourcentageCompletion(calculerPourcentageCompletion(inscriptionId));
        inscription.setEtapeActuelle(numeroEtape + 1);
        inscriptionRepository.save(inscription);
        
        return toDTO(etape);
    }

    @Override
    public Double calculerPourcentageCompletion(Long inscriptionId) {
        List<EtapeInscription> etapes = etapeRepository.findByInscriptionIdOrderByNumeroEtape(inscriptionId);
        long completees = etapes.stream().mapToLong(e -> e.getCompletee() ? 1 : 0).sum();
        return (double) completees / etapes.size() * 100;
    }
    
    private EtapeInscriptionDTO toDTO(EtapeInscription etape) {
        EtapeInscriptionDTO dto = new EtapeInscriptionDTO();
        dto.setId(etape.getId());
        dto.setNumeroEtape(etape.getNumeroEtape());
        dto.setNomEtape(etape.getNomEtape());
        dto.setCompletee(etape.getCompletee());
        dto.setDateCompletion(etape.getDateCompletion());
        dto.setInscriptionId(etape.getInscription().getId());
        return dto;
    }
}