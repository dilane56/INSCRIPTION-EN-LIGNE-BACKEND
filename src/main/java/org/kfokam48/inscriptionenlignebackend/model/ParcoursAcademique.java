package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ParcoursAcademique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String etablissement;
    private String specialisation;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String diplomeObtenu;
    
    @ManyToOne
    @JoinColumn(name = "candidat_id")
    private Candidat candidat;
}