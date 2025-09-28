package org.kfokam48.inscriptionenlignebackend.service.impl;

import org.kfokam48.inscriptionenlignebackend.enums.StatutInscription;
import org.kfokam48.inscriptionenlignebackend.model.Admin;
import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.kfokam48.inscriptionenlignebackend.repository.AdminRepository;
import org.kfokam48.inscriptionenlignebackend.repository.InscriptionRepository;
import org.kfokam48.inscriptionenlignebackend.service.NotificationService;
import org.kfokam48.inscriptionenlignebackend.service.WorkflowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class WorkflowServiceImpl implements WorkflowService {
    
    private final InscriptionRepository inscriptionRepository;
    private final AdminRepository adminRepository;
    private final NotificationService notificationService;
    
    public WorkflowServiceImpl(InscriptionRepository inscriptionRepository, 
                              AdminRepository adminRepository,
                              NotificationService notificationService) {
        this.inscriptionRepository = inscriptionRepository;
        this.adminRepository = adminRepository;
        this.notificationService = notificationService;
    }

    @Override
    public void changerStatut(Long inscriptionId, StatutInscription nouveauStatut, String commentaire, Long adminId) {
        Inscription inscription = inscriptionRepository.findById(inscriptionId).orElseThrow();
        inscription.setStatut(nouveauStatut);
        inscription.setCommentaireAdmin(commentaire);
        inscription.setDerniereModification(LocalDateTime.now());
        
        if (adminId != null) {
            Admin admin = adminRepository.findById(adminId).orElseThrow();
            inscription.setAdminValidateur(admin);
        }
        
        inscriptionRepository.save(inscription);
    }

    @Override
    public void soumettreInscription(Long inscriptionId) {
        Inscription inscription = inscriptionRepository.findById(inscriptionId).orElseThrow();
        inscription.setStatut(StatutInscription.SOUMISE);
        inscription.setDateSoumission(LocalDateTime.now());
        inscription.setDerniereModification(LocalDateTime.now());
        inscriptionRepository.save(inscription);
        
        // Envoyer notification de soumission via l'API existante
        if (inscription.getCandidat() != null && inscription.getCandidat().getId() != null) {
            Long candidatId = inscription.getCandidat().getId();
            String titre = "Inscription soumise";
            String message = "Votre inscription (id=" + inscription.getId() + ") a été soumise avec succès.";
            String type = "INFO";
            notificationService.createNotification(candidatId, titre, message, type);
        }
    }

    @Override
    public void validerInscription(Long inscriptionId, Long adminId, String commentaire) {
        changerStatut(inscriptionId, StatutInscription.VALIDEE, commentaire, adminId);
        
        Inscription inscription = inscriptionRepository.findById(inscriptionId).orElseThrow();
        inscription.setDateValidation(LocalDateTime.now());
        inscriptionRepository.save(inscription);
        
        // Envoyer notification de validation via l'API existante
        if (inscription.getCandidat() != null && inscription.getCandidat().getId() != null) {
            Long candidatId = inscription.getCandidat().getId();
            String titre = "Inscription validée";
            String message = "Félicitations — votre inscription (id=" + inscription.getId() + ") a été validée.";
            String type = "SUCCESS";
            notificationService.createNotification(candidatId, titre, message, type);
        }
    }

    @Override
    public void rejeterInscription(Long inscriptionId, Long adminId, String commentaire) {
        changerStatut(inscriptionId, StatutInscription.REJETEE, commentaire, adminId);
        
        // Envoyer notification de rejet via l'API existante
        Inscription inscription = inscriptionRepository.findById(inscriptionId).orElseThrow();
        if (inscription.getCandidat() != null && inscription.getCandidat().getId() != null) {
            Long candidatId = inscription.getCandidat().getId();
            String titre = "Inscription rejetée";
            String message = "Votre inscription (id=" + inscription.getId() + ") a été rejetée. Motif: " + (commentaire == null ? "-" : commentaire);
            String type = "ALERT";
            notificationService.createNotification(candidatId, titre, message, type);
        }
    }
}