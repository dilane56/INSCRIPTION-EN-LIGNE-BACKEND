package org.kfokam48.inscriptionenlignebackend.dto.audit;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuditLogDTO {
    private Long id;
    private String action;
    private String entite;
    private Long entiteId;
    private String utilisateur;
    private String adresseIp;
    private String userAgent;
    private LocalDateTime dateAction;
    private String details;
}