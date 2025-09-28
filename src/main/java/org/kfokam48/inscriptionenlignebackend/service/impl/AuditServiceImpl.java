package org.kfokam48.inscriptionenlignebackend.service.impl;

import org.kfokam48.inscriptionenlignebackend.dto.audit.AuditLogDTO;
import org.kfokam48.inscriptionenlignebackend.model.AuditLog;
import org.kfokam48.inscriptionenlignebackend.repository.AuditLogRepository;
import org.kfokam48.inscriptionenlignebackend.service.AuditService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements AuditService {
    
    private final AuditLogRepository auditLogRepository;
    
    public AuditServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }
    
    @Override
    public Page<AuditLogDTO> getAllAuditLogs(Pageable pageable) {
        Page<AuditLog> logs = auditLogRepository.findAllByOrderByDateActionDesc(pageable);
        return logs.map(this::toDTO);
    }
    
    @Override
    public Page<AuditLogDTO> getConnexionLogs(Pageable pageable) {
        Page<AuditLog> logs = auditLogRepository.findByActionContainingOrderByDateActionDesc("LOGIN", pageable);
        return logs.map(this::toDTO);
    }
    
    private AuditLogDTO toDTO(AuditLog auditLog) {
        AuditLogDTO dto = new AuditLogDTO();
        dto.setId(auditLog.getId());
        dto.setAction(auditLog.getAction());
        dto.setEntite(auditLog.getEntite());
        dto.setEntiteId(auditLog.getEntiteId());
        dto.setUtilisateur(auditLog.getUtilisateur());
        dto.setAdresseIp(auditLog.getAdresseIp());
        dto.setUserAgent(auditLog.getUserAgent());
        dto.setDateAction(auditLog.getDateAction());
        dto.setDetails(auditLog.getDetails());
        return dto;
    }
}