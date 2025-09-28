package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.model.Notification;

import java.util.List;

public interface NotificationService {
    void createNotification(Long candidatId, String titre, String message, String type);
    List<Notification> getNotificationsByCandidat(Long candidatId);
    List<Notification> getUnreadNotifications(Long candidatId);
    void markAsRead(Long notificationId);
    long getUnreadCount(Long candidatId);
    List<Notification> getAllNotificationsWithCandidatInfo();
}