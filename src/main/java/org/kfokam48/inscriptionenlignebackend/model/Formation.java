package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Formation {
    @Id
    @GeneratedValue
    private Long id;

    private String nomFormation;
    private String etablissement;
    private String description;
    private Integer duree; // en mois
    private Double prix;
    private String prerequis;


    @OneToMany(mappedBy = "formation")
    @JsonIgnore
    private List<Inscription> inscriptions= new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;
    
    // Méthode helper pour le nom complet
    public String getNom() {
        return nomFormation;
    }
}

