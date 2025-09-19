package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.kfokam48.inscriptionenlignebackend.repository.InscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class ProgressionService {
    
    private final InscriptionRepository inscriptionRepository;
    
    public ProgressionService(InscriptionRepository inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }
    
    public void updateProgression(Long inscriptionId, int etapeActuelle) {
        Inscription inscription = inscriptionRepository.findById(inscriptionId)
                .orElseThrow(() -> new RuntimeException("Inscription not found"));
        
        inscription.setEtapeActuelle(etapeActuelle);
        inscription.setPourcentageCompletion(calculateProgression(etapeActuelle));
        inscription.setDerniereModification(java.time.LocalDateTime.now());
        
        inscriptionRepository.save(inscription);
    }
    
    private Double calculateProgression(int etapeActuelle) {
        // 5 étapes au total : chaque étape = 20%
        return (double) (etapeActuelle - 1) * 20.0;
    }
}