package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.model.AuditLog;
import org.kfokam48.inscriptionenlignebackend.repository.AuditLogRepository;
import org.kfokam48.inscriptionenlignebackend.service.FraudeDetectionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/security")
@CrossOrigin("*")
@PreAuthorize("hasRole('ADMIN')")
public class SecurityController {
    
    private final AuditLogRepository auditLogRepository;
    private final FraudeDetectionService fraudeService;
    
    public SecurityController(AuditLogRepository auditLogRepository, FraudeDetectionService fraudeService) {
        this.auditLogRepository = auditLogRepository;
        this.fraudeService = fraudeService;
    }
    
    @GetMapping("/audit-logs")
    public ResponseEntity<Page<AuditLog>> getAuditLogs(Pageable pageable) {
        return ResponseEntity.ok(auditLogRepository.findAll(pageable));
    }
    
    @GetMapping("/audit-logs/user/{utilisateur}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByUser(@PathVariable String utilisateur) {
        return ResponseEntity.ok(auditLogRepository.findByUtilisateurOrderByDateActionDesc(utilisateur));
    }
    
    @GetMapping("/audit-logs/periode")
    public ResponseEntity<List<AuditLog>> getAuditLogsByPeriode(
            @RequestParam LocalDateTime debut,
            @RequestParam LocalDateTime fin) {
        return ResponseEntity.ok(auditLogRepository.findByDateActionBetween(debut, fin));
    }
    
    @PostMapping("/marquer-suspect/{inscriptionId}")
    public ResponseEntity<String> marquerSuspect(@PathVariable Long inscriptionId, @RequestParam String raison) {
        fraudeService.marquerCommeSuspect(inscriptionId, raison);
        return ResponseEntity.ok("Inscription marquée comme suspecte");
    }
}