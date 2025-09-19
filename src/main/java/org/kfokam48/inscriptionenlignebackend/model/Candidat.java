package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kfokam48.inscriptionenlignebackend.enums.Sexe;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Candidat extends User {
    private LocalDate dateNaissance;
    private String nationalite;
    private String typeDePieceIdentite;
    private String numeroPieceIdentite;
    private String adresse;
    private String ville;
    private String codePostal;
    private String pays;
    private String contactPourUrgence;
    private String telephoneUrgence;
    private Sexe sexe;
    private LocalDateTime dateCreation = LocalDateTime.now();
    private LocalDateTime derniereConnexion;
    
    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Inscription> inscriptions = new ArrayList<>();
    
    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ParcoursAcademique> parcoursAcademiques = new ArrayList<>();
}

