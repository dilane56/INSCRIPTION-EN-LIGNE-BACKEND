package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Document {
    @Id
    @GeneratedValue
    private Long id;

    private String typeDocument; // "CNI recto", "Diplôme", etc.
    private String fichierUrl;
    private Boolean formatValide;
    private Boolean valideParOCR;
    private String commentaire;

    @ManyToOne
    private Inscription inscription;

    // Getters, setters, constructeurs
}

