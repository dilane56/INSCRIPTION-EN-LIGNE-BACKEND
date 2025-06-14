package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue
    private Long id;

    private String type; // Email, SMS, in-app
    private String contenu;
    private String statut; // envoyé / échec
    private LocalDateTime dateEnvoi;

    @ManyToOne
    private Inscription inscription;

    // Getters, setters, constructeurs
}

