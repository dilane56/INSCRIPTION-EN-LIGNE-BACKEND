package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Inscription {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate dateInscription;
    private String etat; // ex: "pré-validée", "en attente", "validée", "rejetée"
    private String anneeAcademique;

    @ManyToOne
    private Candidat candidat;


    @ManyToOne
    private Formation formation;

    @OneToMany(mappedBy = "inscription", cascade = CascadeType.ALL)
    private List<Document> documents;

    @OneToMany(mappedBy = "inscription", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    // Getters, setters, constructeurs
}

