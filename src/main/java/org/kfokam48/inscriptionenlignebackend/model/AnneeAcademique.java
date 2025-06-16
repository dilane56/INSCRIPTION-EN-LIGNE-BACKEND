package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class AnneeAcademique {
    @Id @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String libelle; // Exemple : "2024-2025"

    @OneToMany(mappedBy = "anneeAcademique")
    private List<Inscription> inscriptions;

    // Getters, setters, constructeur
}
