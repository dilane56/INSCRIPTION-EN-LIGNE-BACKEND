package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.notification.NotificationDTO;
import org.kfokam48.inscriptionenlignebackend.enums.TypeNotification;
import org.kfokam48.inscriptionenlignebackend.model.Inscription;

import java.util.List;

public interface NotificationService {
    void creerNotification(Long inscriptionId, TypeNotification type, String titre, String contenu);
    void envoyerNotificationSoumission(Inscription inscription);
    void envoyerNotificationValidation(Inscription inscription);
    void envoyerNotificationRejet(Inscription inscription);
    void envoyerRappelInscription(Inscription inscription);
    List<NotificationDTO> getNotificationsCandidat(Long candidatId);
    void marquerCommeLue(Long notificationId);
}
