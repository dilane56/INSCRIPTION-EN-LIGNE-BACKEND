package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        git @Table(uniqueConstraints = {
                @UniqueConstraint(columnNames = {"candidat_id", "formation_id"})
        })

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

    @OneToMany(mappedBy = "inscription", cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "inscription", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    // ...
}

