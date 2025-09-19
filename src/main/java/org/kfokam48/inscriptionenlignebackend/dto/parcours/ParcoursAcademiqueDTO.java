package org.kfokam48.inscriptionenlignebackend.dto.parcours;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ParcoursAcademiqueDTO {
    private Long id;
    private String etablissement;
    private String specialisation;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String diplomeObtenu;
    private Long candidatId;
}