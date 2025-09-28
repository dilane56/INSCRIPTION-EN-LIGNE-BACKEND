package org.kfokam48.inscriptionenlignebackend.controller;

import lombok.RequiredArgsConstructor;
import org.kfokam48.inscriptionenlignebackend.dto.formation.FormationRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.formation.FormationResponseDTO;
import org.kfokam48.inscriptionenlignebackend.service.FormationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formations")
@RequiredArgsConstructor
public class FormationController {
    
    private final FormationService formationService;
    
    @GetMapping
    public ResponseEntity<List<FormationResponseDTO>> getAllFormations() {
        return ResponseEntity.ok(formationService.getAllFormations());
    }
    
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FormationResponseDTO>> getAllFormationsForAdmin() {
        return ResponseEntity.ok(formationService.getAllFormations());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FormationResponseDTO> getFormationById(@PathVariable Long id) {
        return ResponseEntity.ok(formationService.getFormationById(id));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FormationResponseDTO> createFormation(@RequestBody FormationRequestDTO formationDTO) {
        return ResponseEntity.ok(formationService.createFormation(formationDTO));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FormationResponseDTO> updateFormation(@PathVariable Long id, @RequestBody FormationRequestDTO formationDTO) {
        return ResponseEntity.ok(formationService.updateFormation(id, formationDTO));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
        return ResponseEntity.ok().build();
    }
}