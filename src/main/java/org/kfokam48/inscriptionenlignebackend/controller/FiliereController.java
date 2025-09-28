package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.filiere.FiliereDTO;
import org.kfokam48.inscriptionenlignebackend.dto.filiere.FiliereRequestDTO;
import org.kfokam48.inscriptionenlignebackend.service.FiliereService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filieres")
public class FiliereController {
    
    private final FiliereService filiereService;
    
    public FiliereController(FiliereService filiereService) {
        this.filiereService = filiereService;
    }
    
    @GetMapping
    public ResponseEntity<List<FiliereDTO>> getAllFilieres() {
        List<FiliereDTO> filieres = filiereService.getAll();
        return ResponseEntity.ok(filieres);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<FiliereDTO>> getAllFilieresAdmin() {
        List<FiliereDTO> filieres = filiereService.getAll();
        return ResponseEntity.ok(filieres);
    }
    
    @PostMapping
    public ResponseEntity<FiliereDTO> createFiliere(@RequestBody FiliereRequestDTO request) {
        FiliereDTO filiere = filiereService.create(request);
        return ResponseEntity.ok(filiere);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<FiliereDTO> updateFiliere(@PathVariable Long id, @RequestBody FiliereRequestDTO request) {
        FiliereDTO filiere = filiereService.update(id, request);
        return ResponseEntity.ok(filiere);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFiliere(@PathVariable Long id) {
        filiereService.delete(id);
        return ResponseEntity.ok("Filière supprimée avec succès");
    }
}