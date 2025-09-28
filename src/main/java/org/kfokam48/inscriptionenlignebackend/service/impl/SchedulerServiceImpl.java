package org.kfokam48.inscriptionenlignebackend.service.impl;

import org.kfokam48.inscriptionenlignebackend.enums.StatutInscription;
import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.kfokam48.inscriptionenlignebackend.repository.InscriptionRepository;
import org.kfokam48.inscriptionenlignebackend.service.NotificationService;
import org.kfokam48.inscriptionenlignebackend.service.SchedulerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {
    
    private final InscriptionRepository inscriptionRepository;
    private final NotificationService notificationService;
    
    public SchedulerServiceImpl(InscriptionRepository inscriptionRepository, 
                               NotificationService notificationService) {
        this.inscriptionRepository = inscriptionRepository;
        this.notificationService = notificationService;
    }

    @Override
    public void planifierRappelInscription(Long inscriptionId, int joursDelai) {
        // Implémentation avec Quartz si nécessaire
        // Pour l'instant, utilisation du scheduler Spring
    }

    @Override
    public void annulerRappelInscription(Long inscriptionId) {
        // Annulation du job Quartz
    }

    @Override
    @Scheduled(cron = "0 0 9 * * ?") // Tous les jours à 9h
    public void executerRappelsQuotidiens() {
        LocalDateTime limiteRappel = LocalDateTime.now().minusDays(3);
        
        List<Inscription> inscriptionsEnRetard = inscriptionRepository
                .findByStatutAndDerniereModificationBefore(StatutInscription.BROUILLON, limiteRappel);
        
        inscriptionsEnRetard.forEach(inscription -> {
            if (inscription.getPourcentageCompletion() < 100) {
                // Utiliser l'API existante de NotificationService
                if (inscription.getCandidat() != null && inscription.getCandidat().getId() != null) {
                    Long candidatId = inscription.getCandidat().getId();
                    String titre = "Rappel : finalisez votre inscription";
                    String message = "Votre dossier d'inscription (id=" + inscription.getId() + ") est incomplet. Merci de le finaliser.";
                    String type = "RAPPEL";
                    notificationService.createNotification(candidatId, titre, message, type);
                }
            }
        });
    }
}