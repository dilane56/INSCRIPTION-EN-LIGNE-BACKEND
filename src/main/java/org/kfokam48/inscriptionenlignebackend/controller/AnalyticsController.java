package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.analytics.DashboardStatsDTO;
import org.kfokam48.inscriptionenlignebackend.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    
    private final AnalyticsService analyticsService;
    
    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }
    
    @GetMapping("/dashboard-stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        DashboardStatsDTO stats = analyticsService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
}