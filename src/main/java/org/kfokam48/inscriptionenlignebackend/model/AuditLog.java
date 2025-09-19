package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String action;
    private String entite;
    private Long entiteId;
    private String utilisateur;
    private String adresseIp;
    private String userAgent;
    private LocalDateTime dateAction = LocalDateTime.now();
    
    @Column(columnDefinition = "TEXT")
    private String details;
}