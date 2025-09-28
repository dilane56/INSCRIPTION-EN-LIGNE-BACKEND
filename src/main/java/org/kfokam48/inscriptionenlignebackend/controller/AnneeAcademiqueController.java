package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.model.AnneeAcademique;
import org.kfokam48.inscriptionenlignebackend.repository.AnneeAcademiqueRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annees-academiques")
public class AnneeAcademiqueController {
    
    private final AnneeAcademiqueRepository anneeAcademiqueRepository;
    
    public AnneeAcademiqueController(AnneeAcademiqueRepository anneeAcademiqueRepository) {
        this.anneeAcademiqueRepository = anneeAcademiqueRepository;
    }
    
    @GetMapping
    public ResponseEntity<List<AnneeAcademique>> getAllAnneesAcademiques() {
        List<AnneeAcademique> annees = anneeAcademiqueRepository.findAll();
        return ResponseEntity.ok(annees);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AnneeAcademique> getAnneeAcademiqueById(@PathVariable Long id) {
        return anneeAcademiqueRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AnneeAcademique> createAnneeAcademique(@RequestBody AnneeAcademique annee) {
        AnneeAcademique saved = anneeAcademiqueRepository.save(annee);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnneeAcademique> updateAnneeAcademique(@PathVariable Long id, @RequestBody AnneeAcademique annee) {
        return anneeAcademiqueRepository.findById(id)
                .map(existing -> {
                    existing.setLibelle(annee.getLibelle());
                    existing.setDateDebut(annee.getDateDebut());
                    existing.setDateFin(annee.getDateFin());
                    existing.setActive(annee.getActive());
                    existing.setDescription(annee.getDescription());
                    existing.setCapaciteMaximale(annee.getCapaciteMaximale());
                    existing.setDateLimiteInscription(annee.getDateLimiteInscription());
                    AnneeAcademique updated = anneeAcademiqueRepository.save(existing);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnneeAcademique(@PathVariable Long id) {
        return anneeAcademiqueRepository.findById(id)
                .map(existing -> {
                    anneeAcademiqueRepository.delete(existing);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}