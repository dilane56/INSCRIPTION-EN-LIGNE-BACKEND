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
}