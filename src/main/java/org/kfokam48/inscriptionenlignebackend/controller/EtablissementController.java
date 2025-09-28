package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.etablissement.EtablissementDTO;
import org.kfokam48.inscriptionenlignebackend.dto.etablissement.EtablissementRequestDTO;
import org.kfokam48.inscriptionenlignebackend.service.EtablissementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etablissements")
public class EtablissementController {
    
    private final EtablissementService etablissementService;
    
    public EtablissementController(EtablissementService etablissementService) {
        this.etablissementService = etablissementService;
    }
    
    @GetMapping
    public ResponseEntity<List<EtablissementDTO>> getAllEtablissements() {
        List<EtablissementDTO> etablissements = etablissementService.getAllActifs();
        return ResponseEntity.ok(etablissements);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<EtablissementDTO>> getAllEtablissementsAdmin() {
        List<EtablissementDTO> etablissements = etablissementService.getAll();
        return ResponseEntity.ok(etablissements);
    }
    
    @PostMapping
    public ResponseEntity<EtablissementDTO> createEtablissement(@RequestBody EtablissementRequestDTO request) {
        EtablissementDTO etablissement = etablissementService.create(request);
        return ResponseEntity.ok(etablissement);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EtablissementDTO> updateEtablissement(@PathVariable Long id, @RequestBody EtablissementRequestDTO request) {
        EtablissementDTO etablissement = etablissementService.update(id, request);
        return ResponseEntity.ok(etablissement);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEtablissement(@PathVariable Long id) {
        etablissementService.delete(id);
        return ResponseEntity.ok("Etablissement supprimé avec succès");
    }
}