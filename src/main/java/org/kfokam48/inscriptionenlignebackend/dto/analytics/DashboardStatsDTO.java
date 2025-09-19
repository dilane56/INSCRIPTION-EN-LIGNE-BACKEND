package org.kfokam48.inscriptionenlignebackend.dto.analytics;

import lombok.Data;

import java.util.Map;

@Data
public class DashboardStatsDTO {
    private long totalInscriptions;
    private long inscriptionsValidees;
    private long inscriptionsEnAttente;
    private long inscriptionsRejetees;
    private double tauxValidation;
    private Map<String, Long> inscriptionsParFormation;
    private Map<Integer, Long> completionParEtape;
    private Map<String, Long> inscriptionsParMois;
}