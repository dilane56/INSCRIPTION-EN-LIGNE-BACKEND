package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.audit.AuditLogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditService {
    Page<AuditLogDTO> getAllAuditLogs(Pageable pageable);
    Page<AuditLogDTO> getConnexionLogs(Pageable pageable);
}