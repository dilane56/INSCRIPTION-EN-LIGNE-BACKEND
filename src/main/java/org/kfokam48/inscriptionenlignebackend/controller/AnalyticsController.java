package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.analytics.DashboardStatsDTO;
import org.kfokam48.inscriptionenlignebackend.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin("*")
@PreAuthorize("hasRole('ADMIN')")
public class AnalyticsController {
    
    private final AnalyticsService analyticsService;
    
    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }
    
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        return ResponseEntity.ok(analyticsService.getDashboardStats());
    }
    
    @GetMapping("/taux-completion")
    public ResponseEntity<Double> getTauxCompletion() {
        return ResponseEntity.ok(analyticsService.getTauxCompletionMoyen());
    }
    
    @GetMapping("/inscriptions-bloquees")
    public ResponseEntity<Long> getInscriptionsBloquees() {
        return ResponseEntity.ok(analyticsService.getNombreInscriptionsBloquees());
    }
}