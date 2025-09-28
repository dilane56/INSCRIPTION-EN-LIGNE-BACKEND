package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kfokam48.inscriptionenlignebackend.enums.TypeDocument;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String nomOriginal;
    
    @Enumerated(EnumType.STRING)
    private TypeDocument typeDocument;
    
    private String mimeType;
    private Long taille;
    @Column(length = 1000)
    private String cheminFichier; // Pour MinIO - URL longue
    private String hashDocument; // Pour détecter les doublons
    
    private Boolean valide = false;
    private String commentaireValidation;
    private LocalDateTime dateUpload = LocalDateTime.now();
    private LocalDateTime dateValidation;
    private String statutValidation;
    
    @ManyToOne
    @JoinColumn(name = "inscription_id")
    private Inscription inscription;
    
    @ManyToOne
    @JoinColumn(name = "admin_validateur_id",nullable = true)

    private Admin adminValidateur;
}


