package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.model.Notification;
import org.kfokam48.inscriptionenlignebackend.model.User;
import org.kfokam48.inscriptionenlignebackend.service.NotificationService;
import org.kfokam48.inscriptionenlignebackend.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    
    private final NotificationService notificationService;
    private final AuthService authService;
    
    public NotificationController(NotificationService notificationService, AuthService authService) {
        this.notificationService = notificationService;
        this.authService = authService;
    }
    
    @GetMapping("/candidat")
    public ResponseEntity<List<Notification>> getCandidatNotifications(Authentication authentication) {
        String email = authentication.getName();
        User user = authService.getUserByEmail(email);
        
        List<Notification> notifications = notificationService.getNotificationsByCandidat(user.getId());
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/candidat/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(Authentication authentication) {
        String email = authentication.getName();
        User user = authService.getUserByEmail(email);
        
        List<Notification> notifications = notificationService.getUnreadNotifications(user.getId());
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/candidat/count")
    public ResponseEntity<Long> getUnreadCount(Authentication authentication) {
        String email = authentication.getName();
        User user = authService.getUserByEmail(email);
        
        long count = notificationService.getUnreadCount(user.getId());
        return ResponseEntity.ok(count);
    }
    
    @PutMapping("/{id}/read")
    public ResponseEntity<String> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok("Notification marquée comme lue");
    }
    
    @GetMapping("/admin/all")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        // Pour l'admin - récupérer toutes les notifications avec infos candidat
        return ResponseEntity.ok(notificationService.getAllNotificationsWithCandidatInfo());
    }
}