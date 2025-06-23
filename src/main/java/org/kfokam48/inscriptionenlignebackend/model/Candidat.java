package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kfokam48.inscriptionenlignebackend.enums.Sexe;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class  Candidat  extends User{
    private LocalDate dateNaissance;
    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL)
    private List<Inscription> inscriptions;
    private String nationalite;
    private String typeDePieceIdentite;
    private String adresse;
    private String contactPourUrgence;
    private Sexe sexe;
}

