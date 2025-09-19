package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admin extends User {
    private String departement;
    private Boolean actif = true;
    private LocalDateTime dateCreation = LocalDateTime.now();
    private LocalDateTime derniereConnexion;
    
    @OneToMany(mappedBy = "adminValidateur")
    private List<Inscription> inscriptionsValidees = new ArrayList<>();
    
    @OneToMany(mappedBy = "adminValidateur")
    private List<Document> documentsValides = new ArrayList<>();
}

