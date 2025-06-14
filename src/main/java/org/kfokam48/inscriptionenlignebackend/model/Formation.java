package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Formation {
    @Id
    @GeneratedValue
    private Long id;

    private String nomFormation;
    private String etablissement;
    private String specialite;
    private String niveau;
    private String anneeAcademique;

    @OneToMany(mappedBy = "programme")
    private List<Inscription> inscriptions;

    // Getters, setters, constructeurs
}

