package org.kfokam48.inscriptionenlignebackend.service.impl;

import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.kfokam48.inscriptionenlignebackend.repository.InscriptionRepository;
import org.kfokam48.inscriptionenlignebackend.service.FraudeDetectionService;
import org.kfokam48.inscriptionenlignebackend.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class FraudeDetectionServiceImpl implements FraudeDetectionService {
    
    private final InscriptionRepository inscriptionRepository;
    private final NotificationService notificationService;
    
    public FraudeDetectionServiceImpl(InscriptionRepository inscriptionRepository, 
                                     NotificationService notificationService) {
        this.inscriptionRepository = inscriptionRepository;
        this.notificationService = notificationService;
    }

    @Override
    public boolean detecterDocumentSuspect(MultipartFile file) {
        // Vérifications basiques
        if (file.getSize() < 1000) { // Fichier trop petit
            return true;
        }
        
        if (file.getOriginalFilename() != null && 
            file.getOriginalFilename().contains("test")) {
            return true;
        }
        
        // Vérification du type MIME vs extension
        String filename = file.getOriginalFilename();
        String contentType = file.getContentType();
        
        if (filename != null && contentType != null) {
            if (filename.endsWith(".pdf") && !contentType.equals("application/pdf")) {
                return true;
            }
            if (filename.matches(".*\\.(jpg|jpeg|png)$") && !contentType.startsWith("image/")) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public boolean detecterInscriptionSuspecte(Inscription inscription) {
        double score = calculerScoreRisque(inscription);
        return score > 70.0; // Seuil de suspicion
    }

    @Override
    public void marquerCommeSuspect(Long inscriptionId, String raison) {
        Inscription inscription = inscriptionRepository.findById(inscriptionId).orElseThrow();
        inscription.setCommentaireAdmin("SUSPECT: " + raison);
        inscriptionRepository.save(inscription);
        
        // Notifier les admins
        // notificationService.notifierAdminsSuspicion(inscription, raison);
    }

    @Override
    public double calculerScoreRisque(Inscription inscription) {
        double score = 0.0;
        
        // Inscription trop rapide (moins de 5 minutes)
        if (inscription.getDateCreation() != null && inscription.getDateSoumission() != null) {
            long minutes = ChronoUnit.MINUTES.between(inscription.getDateCreation(), inscription.getDateSoumission());
            if (minutes < 5) {
                score += 30.0;
            }
        }
        
        // Données personnelles suspectes
        if (inscription.getCandidat().getFirstName().matches(".*\\d.*")) { // Nom avec chiffres
            score += 20.0;
        }
        
        // Email suspect
        String email = inscription.getCandidat().getEmail();
        if (email.contains("temp") || email.contains("fake") || email.contains("test")) {
            score += 25.0;
        }
        
        // Téléphone invalide
        String phone = inscription.getCandidat().getPhone();
        if (phone != null && !phone.matches("^[+]?[0-9]{8,15}$")) {
            score += 15.0;
        }
        
        // Trop de modifications récentes
        if (inscription.getDerniereModification() != null) {
            long heures = ChronoUnit.HOURS.between(inscription.getDateCreation(), inscription.getDerniereModification());
            if (heures > 24) {
                score += 10.0;
            }
        }
        
        return Math.min(score, 100.0);
    }
}