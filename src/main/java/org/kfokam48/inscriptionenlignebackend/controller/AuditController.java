package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.audit.AuditLogDTO;
import org.kfokam48.inscriptionenlignebackend.service.AuditService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit")
@PreAuthorize("hasRole('ADMIN')")
public class AuditController {
    
    private final AuditService auditService;
    
    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }
    
    @GetMapping("/logs")
    public ResponseEntity<Page<AuditLogDTO>> getAuditLogs(Pageable pageable) {
        Page<AuditLogDTO> logs = auditService.getAllAuditLogs(pageable);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/connexions")
    public ResponseEntity<Page<AuditLogDTO>> getConnexionLogs(Pageable pageable) {
        Page<AuditLogDTO> logs = auditService.getConnexionLogs(pageable);
        return ResponseEntity.ok(logs);
    }
}