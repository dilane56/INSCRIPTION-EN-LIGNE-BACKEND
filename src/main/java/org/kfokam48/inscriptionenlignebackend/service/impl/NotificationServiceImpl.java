package org.kfokam48.inscriptionenlignebackend.service.impl;

import org.kfokam48.inscriptionenlignebackend.dto.notification.NotificationDTO;
import org.kfokam48.inscriptionenlignebackend.enums.TypeNotification;
import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.kfokam48.inscriptionenlignebackend.model.Notification;
import org.kfokam48.inscriptionenlignebackend.repository.InscriptionRepository;
import org.kfokam48.inscriptionenlignebackend.repository.NotificationRepository;
import org.kfokam48.inscriptionenlignebackend.service.EmailService;
import org.kfokam48.inscriptionenlignebackend.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
    
    private final NotificationRepository notificationRepository;
    private final InscriptionRepository inscriptionRepository;
    private final EmailService emailService;
    
    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                 InscriptionRepository inscriptionRepository,
                                 EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.inscriptionRepository = inscriptionRepository;
        this.emailService = emailService;
    }

    @Override
    public void creerNotification(Long inscriptionId, TypeNotification type, String titre, String contenu) {
        Inscription inscription = inscriptionRepository.findById(inscriptionId).orElseThrow();
        
        Notification notification = new Notification();
        notification.setType(type);
        notification.setTitre(titre);
        notification.setContenu(contenu);
        notification.setInscription(inscription);
        notification.setCandidat(inscription.getCandidat());
        notification.setDestinataire(inscription.getCandidat().getEmail());
        notification.setStatut("CREE");
        
        notificationRepository.save(notification);
    }

    @Override
    public void envoyerNotificationSoumission(Inscription inscription) {
        creerNotification(inscription.getId(), TypeNotification.IN_APP, 
                         "Inscription soumise", 
                         "Votre inscription a été soumise avec succès");
        
        emailService.envoyerEmailSoumission(inscription);
    }

    @Override
    public void envoyerNotificationValidation(Inscription inscription) {
        creerNotification(inscription.getId(), TypeNotification.IN_APP,
                         "Inscription validée",
                         "Félicitations ! Votre inscription a été validée");
        
        emailService.envoyerEmailValidation(inscription);
    }

    @Override
    public void envoyerNotificationRejet(Inscription inscription) {
        creerNotification(inscription.getId(), TypeNotification.IN_APP,
                         "Inscription non retenue",
                         "Votre inscription n'a pas été retenue. Consultez les détails.");
        
        emailService.envoyerEmailRejet(inscription);
    }

    @Override
    public void envoyerRappelInscription(Inscription inscription) {
        creerNotification(inscription.getId(), TypeNotification.IN_APP,
                         "Complétez votre inscription",
                         "N'oubliez pas de compléter votre inscription");
        
        emailService.envoyerEmailRappel(inscription);
    }

    @Override
    public List<NotificationDTO> getNotificationsCandidat(Long candidatId) {
        return notificationRepository.findByCandidatIdOrderByDateCreationDesc(candidatId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void marquerCommeLue(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow();
        notification.setLue(true);
        notification.setDateLecture(LocalDateTime.now());
        notificationRepository.save(notification);
    }
    
    private NotificationDTO toDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setType(notification.getType());
        dto.setTitre(notification.getTitre());
        dto.setContenu(notification.getContenu());
        dto.setStatut(notification.getStatut());
        dto.setDateCreation(notification.getDateCreation());
        dto.setDateEnvoi(notification.getDateEnvoi());
        dto.setDateLecture(notification.getDateLecture());
        dto.setLue(notification.getLue());
        dto.setInscriptionId(notification.getInscription() != null ? notification.getInscription().getId() : null);
        dto.setCandidatId(notification.getCandidat() != null ? notification.getCandidat().getId() : null);
        return dto;
    }
}
