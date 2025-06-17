package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
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
