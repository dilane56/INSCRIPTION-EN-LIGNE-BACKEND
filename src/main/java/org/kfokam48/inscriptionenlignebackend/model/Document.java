package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Document {
    @Id @GeneratedValue
    private Long id;

    private String nom;
    private String type;
    private String commentaire;

    @Lob
    private byte[] data;

    @ManyToOne
    private Inscription inscription;
}


