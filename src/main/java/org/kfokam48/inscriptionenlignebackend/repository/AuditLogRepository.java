package org.kfokam48.inscriptionenlignebackend.repository;

import org.kfokam48.inscriptionenlignebackend.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByUtilisateurOrderByDateActionDesc(String utilisateur);
    org.springframework.data.domain.Page<AuditLog> findAllByOrderByDateActionDesc(org.springframework.data.domain.Pageable pageable);
    org.springframework.data.domain.Page<AuditLog> findByActionContainingOrderByDateActionDesc(String action, org.springframework.data.domain.Pageable pageable);
    List<AuditLog> findByEntiteAndEntiteIdOrderByDateActionDesc(String entite, Long entiteId);
    List<AuditLog> findByDateActionBetween(LocalDateTime debut, LocalDateTime fin);
}