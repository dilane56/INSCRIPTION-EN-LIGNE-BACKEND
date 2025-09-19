package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.analytics.DashboardStatsDTO;

public interface AnalyticsService {
    DashboardStatsDTO getDashboardStats();
    double getTauxCompletionMoyen();
    long getNombreInscriptionsBloquees();
}