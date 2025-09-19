package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kfokam48.inscriptionenlignebackend.enums.StatutInscription;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"candidat_id", "formation_id", "annee_academique_id"})
})
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime dateCreation = LocalDateTime.now();
    private LocalDateTime dateSoumission;
    private LocalDateTime dateValidation;
    private LocalDateTime derniereModification = LocalDateTime.now();
    
    @Enumerated(EnumType.STRING)
    private StatutInscription statut = StatutInscription.BROUILLON;
    
    private String commentaireAdmin;
    private Integer etapeActuelle = 1;
    private Double pourcentageCompletion = 0.0;
    
    @ManyToOne
    @JoinColumn(name = "candidat_id")
    private Candidat candidat;
    
    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;
    
    @ManyToOne
    @JoinColumn(name = "annee_academique_id")
    private AnneeAcademique anneeAcademique;
    
    @ManyToOne
    @JoinColumn(name = "admin_validateur_id")
    private Admin adminValidateur;
    
    @OneToMany(mappedBy = "inscription", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Document> documents = new ArrayList<>();
    
    @OneToMany(mappedBy = "inscription", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notification> notifications = new ArrayList<>();
    
    @OneToMany(mappedBy = "inscription", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EtapeInscription> etapes = new ArrayList<>();
}

