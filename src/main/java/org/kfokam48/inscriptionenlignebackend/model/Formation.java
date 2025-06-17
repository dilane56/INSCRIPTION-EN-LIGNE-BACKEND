package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String specialite;
    private String niveau;
    @OneToMany(mappedBy = "formation")
    private List<Inscription> inscriptions;

    // Getters, setters, constructeurs
}

