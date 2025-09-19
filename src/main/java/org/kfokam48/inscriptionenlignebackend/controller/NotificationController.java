package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.notification.NotificationDTO;
import org.kfokam48.inscriptionenlignebackend.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin("*")
public class NotificationController {
    
    private final NotificationService notificationService;
    
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    
    @GetMapping("/candidat/{candidatId}")
    public ResponseEntity<List<NotificationDTO>> getNotifications(@PathVariable Long candidatId) {
        return ResponseEntity.ok(notificationService.getNotificationsCandidat(candidatId));
    }
    
    @PostMapping("/marquer-lue/{notificationId}")
    public ResponseEntity<String> marquerCommeLue(@PathVariable Long notificationId) {
        notificationService.marquerCommeLue(notificationId);
        return ResponseEntity.ok("Notification marquée comme lue");
    }
}