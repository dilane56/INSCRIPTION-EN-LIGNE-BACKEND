package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kfokam48.inscriptionenlignebackend.enums.TypeNotification;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private TypeNotification type;
    
    private String titre;
    private String contenu;
    private String destinataire; // email ou téléphone
    private String statut = "EN_ATTENTE"; // EN_ATTENTE, ENVOYE, ECHEC
    private String messageErreur;
    
    private LocalDateTime dateCreation = LocalDateTime.now();
    private LocalDateTime dateEnvoi;
    private LocalDateTime dateLecture;
    
    private Boolean lue = false;
    
    @ManyToOne
    @JoinColumn(name = "inscription_id")
    private Inscription inscription;
    
    @ManyToOne
    @JoinColumn(name = "candidat_id")
    private Candidat candidat;
}

