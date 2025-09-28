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
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titre;
    
    @Column(columnDefinition = "TEXT")
    private String message;
    
    private String type; // DOCUMENT_VALIDE, DOCUMENT_REJETE, DOSSIER_VALIDE, DOSSIER_REJETE
    
    private Long candidatId;
    
    private Boolean lue = false;
    
    private LocalDateTime dateCreation = LocalDateTime.now();
    
    private LocalDateTime dateLecture;

    // Relation vers Inscription — correspond au mappedBy = "inscription" dans Inscription.notifications
    @ManyToOne
    @JoinColumn(name = "inscription_id")
    private Inscription inscription;
}