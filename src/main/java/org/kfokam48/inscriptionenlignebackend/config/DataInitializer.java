package org.kfokam48.inscriptionenlignebackend.config;

import org.kfokam48.inscriptionenlignebackend.model.AnneeAcademique;
import org.kfokam48.inscriptionenlignebackend.model.Formation;
import org.kfokam48.inscriptionenlignebackend.repository.AnneeAcademiqueRepository;
import org.kfokam48.inscriptionenlignebackend.repository.FormationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final FormationRepository formationRepository;
    private final AnneeAcademiqueRepository anneeAcademiqueRepository;
    
    public DataInitializer(FormationRepository formationRepository, 
                          AnneeAcademiqueRepository anneeAcademiqueRepository) {
        this.formationRepository = formationRepository;
        this.anneeAcademiqueRepository = anneeAcademiqueRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        initializeFormations();
        initializeAnneesAcademiques();
    }
    
    private void initializeFormations() {
        if (formationRepository.count() == 0) {
            Formation formation1 = new Formation();
            formation1.setNomFormation("Master Informatique");
            formation1.setEtablissement("Université Technologique");
            formation1.setSpecialite("Développement Logiciel");
            formation1.setDescription("Master en Sciences Informatiques - Spécialité Développement Logiciel");
            formation1.setDuree(24);
            formation1.setPrix(8000.0);
            formation1.setPrerequis("Licence en Informatique ou équivalent");
            
            Formation formation2 = new Formation();
            formation2.setNomFormation("Master Data Science");
            formation2.setEtablissement("École Supérieure de Technologie");
            formation2.setSpecialite("Intelligence Artificielle");
            formation2.setDescription("Master en Science des Données et Intelligence Artificielle");
            formation2.setDuree(24);
            formation2.setPrix(9000.0);
            formation2.setPrerequis("Licence en Mathématiques, Informatique ou Statistiques");
            
            Formation formation3 = new Formation();
            formation3.setNomFormation("MBA Management");
            formation3.setEtablissement("Business School");
            formation3.setSpecialite("Leadership");
            formation3.setDescription("Master of Business Administration - Management et Leadership");
            formation3.setDuree(18);
            formation3.setPrix(12000.0);
            formation3.setPrerequis("Licence + 3 ans d'expérience professionnelle");
            
            formationRepository.save(formation1);
            formationRepository.save(formation2);
            formationRepository.save(formation3);
            
            System.out.println("✅ Formations initialisées");
        }
    }
    
    private void initializeAnneesAcademiques() {
        if (anneeAcademiqueRepository.count() == 0) {
            AnneeAcademique annee2024 = new AnneeAcademique();
            annee2024.setLibelle("2024-2025");
            annee2024.setDateDebut(LocalDate.of(2024, 9, 1));
            annee2024.setDateFin(LocalDate.of(2025, 6, 30));
            annee2024.setActive(true);
            annee2024.setDescription("Année académique 2024-2025 - Inscriptions ouvertes");
            annee2024.setCapaciteMaximale(500);
            annee2024.setDateLimiteInscription(LocalDate.of(2024, 8, 15));
            
            AnneeAcademique annee2025 = new AnneeAcademique();
            annee2025.setLibelle("2025-2026");
            annee2025.setDateDebut(LocalDate.of(2025, 9, 1));
            annee2025.setDateFin(LocalDate.of(2026, 6, 30));
            annee2025.setActive(false);
            annee2025.setDescription("Année académique 2025-2026 - Inscriptions fermées");
            annee2025.setCapaciteMaximale(600);
            annee2025.setDateLimiteInscription(LocalDate.of(2025, 8, 15));
            
            anneeAcademiqueRepository.save(annee2024);
            anneeAcademiqueRepository.save(annee2025);
            
            System.out.println("✅ Années académiques initialisées");
        }
    }
}