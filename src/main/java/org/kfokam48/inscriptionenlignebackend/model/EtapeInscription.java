package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EtapeInscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer numeroEtape; // 1-5
    private String nomEtape;
    private Boolean completee = false;
    private LocalDateTime dateCompletion;
    
    @ManyToOne
    @JoinColumn(name = "inscription_id")
    private Inscription inscription;
}