package org.kfokam48.inscriptionenlignebackend.service.impl;

import org.kfokam48.inscriptionenlignebackend.model.Notification;
import org.kfokam48.inscriptionenlignebackend.repository.NotificationRepository;
import org.kfokam48.inscriptionenlignebackend.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    
    private final NotificationRepository notificationRepository;
    
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    
    @Override
    public void createNotification(Long candidatId, String titre, String message, String type) {
        Notification notification = new Notification();
        notification.setCandidatId(candidatId);
        notification.setTitre(titre);
        notification.setMessage(message);
        notification.setType(type);
        notification.setDateCreation(LocalDateTime.now());
        notification.setLue(false);
        
        notificationRepository.save(notification);
    }
    
    @Override
    public List<Notification> getNotificationsByCandidat(Long candidatId) {
        return notificationRepository.findByCandidatIdOrderByDateCreationDesc(candidatId);
    }
    
    @Override
    public List<Notification> getUnreadNotifications(Long candidatId) {
        return notificationRepository.findByCandidatIdAndLueFalseOrderByDateCreationDesc(candidatId);
    }
    
    @Override
    public void markAsRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setLue(true);
            notification.setDateLecture(LocalDateTime.now());
            notificationRepository.save(notification);
        });
    }
    
    @Override
    public long getUnreadCount(Long candidatId) {
        return notificationRepository.countByCandidatIdAndLueFalse(candidatId);
    }
    
    @Override
    public List<Notification> getAllNotificationsWithCandidatInfo() {
        return notificationRepository.findAllByOrderByDateCreationDesc();
    }
}