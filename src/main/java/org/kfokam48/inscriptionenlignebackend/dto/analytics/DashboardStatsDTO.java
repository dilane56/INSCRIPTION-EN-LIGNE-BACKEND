package org.kfokam48.inscriptionenlignebackend.dto.analytics;

import lombok.Data;

@Data
public class DashboardStatsDTO {
    private Long totalInscriptions;
    private Long inscriptionsEnAttente;
    private Long inscriptionsValidees;
    private Long inscriptionsRejetees;
    private Long totalCandidats;
    private Long totalFormations;
    private Double tauxValidation;
    private Long inscriptionsAujourdhui;
    private java.util.Map<String, Long> inscriptionsParFormation;
    private java.util.Map<Integer, Long> completionParEtape;
    private java.util.Map<String, Long> inscriptionsParMois;
}