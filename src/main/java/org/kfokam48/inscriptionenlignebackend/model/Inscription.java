package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"candidat_id", "annee_academique_id"})
        }
)
public class Inscription {
    @Id @GeneratedValue
    private Long id;

    private LocalDate dateSoumission;
    private String etat;
    @ManyToOne
    private Candidat candidat;

    @ManyToOne
    private Formation formation;

    @ManyToOne
    @JoinColumn(name = "annee_academique_id")
    private AnneeAcademique anneeAcademique;

    @OneToMany(mappedBy = "inscription", cascade = CascadeType.ALL)
    private List<Document> documents;

    @OneToMany(mappedBy = "inscription", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    // ...
}

