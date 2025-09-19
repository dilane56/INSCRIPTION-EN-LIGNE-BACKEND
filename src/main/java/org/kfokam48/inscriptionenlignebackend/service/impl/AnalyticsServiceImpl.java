package org.kfokam48.inscriptionenlignebackend.service.impl;

import org.kfokam48.inscriptionenlignebackend.dto.analytics.DashboardStatsDTO;
import org.kfokam48.inscriptionenlignebackend.enums.StatutInscription;
import org.kfokam48.inscriptionenlignebackend.repository.InscriptionRepository;
import org.kfokam48.inscriptionenlignebackend.service.AnalyticsService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    
    private final InscriptionRepository inscriptionRepository;
    private final JdbcTemplate jdbcTemplate;
    
    public AnalyticsServiceImpl(InscriptionRepository inscriptionRepository, JdbcTemplate jdbcTemplate) {
        this.inscriptionRepository = inscriptionRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();
        
        // Statistiques de base
        stats.setTotalInscriptions(inscriptionRepository.count());
        stats.setInscriptionsValidees(inscriptionRepository.countByStatut(StatutInscription.VALIDEE));
        stats.setInscriptionsEnAttente(inscriptionRepository.countByStatut(StatutInscription.SOUMISE));
        stats.setInscriptionsRejetees(inscriptionRepository.countByStatut(StatutInscription.REJETEE));
        
        // Taux de validation
        if (stats.getTotalInscriptions() > 0) {
            stats.setTauxValidation((double) stats.getInscriptionsValidees() / stats.getTotalInscriptions() * 100);
        }
        
        // Inscriptions par formation
        stats.setInscriptionsParFormation(getInscriptionsParFormation());
        
        // Completion par étape
        stats.setCompletionParEtape(getCompletionParEtape());
        
        // Inscriptions par mois
        stats.setInscriptionsParMois(getInscriptionsParMois());
        
        return stats;
    }

    @Override
    public double getTauxCompletionMoyen() {
        String sql = "SELECT AVG(pourcentage_completion) FROM inscription WHERE statut != 'BROUILLON'";
        try {
            Double result = jdbcTemplate.queryForObject(sql, Double.class);
            return result != null ? result : 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Override
    public long getNombreInscriptionsBloquees() {
        LocalDateTime limite = LocalDateTime.now().minusHours(48);
        return inscriptionRepository.findByStatutAndDerniereModificationBefore(StatutInscription.SOUMISE, limite).size();
    }
    
    private Map<String, Long> getInscriptionsParFormation() {
        try {
            String sql = "SELECT f.nom, COUNT(i.id) as count FROM inscription i JOIN formation f ON i.formation_id = f.id GROUP BY f.nom";
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
            
            Map<String, Long> map = new HashMap<>();
            results.forEach(row -> {
                String formation = (String) row.get("nom");
                Long count = ((Number) row.get("count")).longValue();
                map.put(formation, count);
            });
            
            return map;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
    
    private Map<Integer, Long> getCompletionParEtape() {
        try {
            String sql = "SELECT etape_actuelle, COUNT(*) as count FROM inscription GROUP BY etape_actuelle";
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
            
            Map<Integer, Long> map = new HashMap<>();
            results.forEach(row -> {
                Integer etape = (Integer) row.get("etape_actuelle");
                Long count = ((Number) row.get("count")).longValue();
                map.put(etape, count);
            });
            
            return map;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
    
    private Map<String, Long> getInscriptionsParMois() {
        try {
            String sql = "SELECT TO_CHAR(date_creation, 'YYYY-MM') as mois, COUNT(*) as count FROM inscription GROUP BY TO_CHAR(date_creation, 'YYYY-MM') ORDER BY mois";
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
            
            Map<String, Long> map = new HashMap<>();
            results.forEach(row -> {
                String mois = (String) row.get("mois");
                Long count = ((Number) row.get("count")).longValue();
                map.put(mois, count);
            });
            
            return map;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
}