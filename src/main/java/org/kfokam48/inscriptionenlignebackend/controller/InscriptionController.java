package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.inscription.InscriptionRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.inscription.InscriptionResponeDTO;
import org.kfokam48.inscriptionenlignebackend.dto.parcours.ParcoursAcademiqueDTO;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.CoordonneesDTO;

import java.util.List;
import java.util.Map;
import org.kfokam48.inscriptionenlignebackend.model.User;
import org.kfokam48.inscriptionenlignebackend.service.InscriptionService;
import org.kfokam48.inscriptionenlignebackend.service.ParcoursAcademiqueService;
import org.kfokam48.inscriptionenlignebackend.service.CandidatService;
import org.kfokam48.inscriptionenlignebackend.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionController {
    
    private final InscriptionService inscriptionService;
    private final ParcoursAcademiqueService parcoursAcademiqueService;
    private final CandidatService candidatService;
    private final AuthService authService;
    
    public InscriptionController(InscriptionService inscriptionService, 
                                ParcoursAcademiqueService parcoursAcademiqueService,
                                CandidatService candidatService,
                                AuthService authService) {
        this.inscriptionService = inscriptionService;
        this.parcoursAcademiqueService = parcoursAcademiqueService;
        this.candidatService = candidatService;
        this.authService = authService;
    }
    
    @PostMapping
    public ResponseEntity<InscriptionResponeDTO> createInscription(
            @RequestBody InscriptionRequestDTO inscriptionRequestDTO,
            Authentication authentication) {
        
        String email = authentication.getName();
        User user = authService.getUserByEmail(email);
        inscriptionRequestDTO.setCandidatId(user.getId());
        
        InscriptionResponeDTO inscription = inscriptionService.save(inscriptionRequestDTO);
        return ResponseEntity.ok(inscription);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<InscriptionResponeDTO> getInscriptionById(@PathVariable Long id) {
        InscriptionResponeDTO inscription = inscriptionService.findById(id);
        return ResponseEntity.ok(inscription);
    }
    
    @PutMapping("/{id}/parcours-academique")
    public ResponseEntity<String> updateParcoursAcademique(
            @PathVariable Long id,
            @RequestBody ParcoursAcademiqueDTO parcoursData,
            Authentication authentication) {
        
        String email = authentication.getName();
        User user = authService.getUserByEmail(email);
        parcoursData.setCandidatId(user.getId());
        
        // Sauvegarder le parcours académique
        parcoursAcademiqueService.save(parcoursData);
        
        // Mettre à jour l'étape de l'inscription et marquer comme complétée
        inscriptionService.updateEtapeActuelle(id, 4);
        inscriptionService.markEtapeComplete(id, 3);
        
        return ResponseEntity.ok("Parcours académique mis à jour avec succès");
    }
    
    @PutMapping("/{id}/coordonnees")
    public ResponseEntity<String> updateCoordonnees(
            @PathVariable Long id,
            @RequestBody CoordonneesDTO coordonneesData,
            Authentication authentication) {
        
        String email = authentication.getName();
        User user = authService.getUserByEmail(email);
        
        // Mettre à jour les coordonnées du candidat
        candidatService.updateCoordonnees(user.getId(), coordonneesData);
        
        // Mettre à jour l'étape de l'inscription et marquer comme complétée
        inscriptionService.updateEtapeActuelle(id, 5);
        inscriptionService.markEtapeComplete(id, 5);
        
        return ResponseEntity.ok("Coordonnées mises à jour avec succès");
    }
    
    @PutMapping("/{id}/etape")
    public ResponseEntity<String> updateEtapeActuelle(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> request) {
        
        Integer etape = request.get("etape");
        inscriptionService.updateEtapeActuelle(id, etape);
        
        return ResponseEntity.ok("Étape mise à jour avec succès");
    }
    
    @GetMapping("/recent")
    public ResponseEntity<List<InscriptionResponeDTO>> getRecentInscriptions() {
        List<InscriptionResponeDTO> inscriptions = inscriptionService.getRecentInscriptions(10);
        return ResponseEntity.ok(inscriptions);
    }
    
    @GetMapping("/admin/all")
    public ResponseEntity<List<InscriptionResponeDTO>> getAllInscriptionsForAdmin() {
        List<InscriptionResponeDTO> inscriptions = inscriptionService.findAll();
        return ResponseEntity.ok(inscriptions);
    }
    
    @PutMapping("/admin/{id}/statut")
    public ResponseEntity<String> updateStatutInscription(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        
        String statut = request.get("statut");
        String commentaire = request.get("commentaire");
        
        inscriptionService.updateStatut(id, statut, commentaire);
        
        return ResponseEntity.ok("Statut mis à jour avec succès");
    }
    
    @PutMapping("/{id}/complete-etape")
    public ResponseEntity<String> completeEtape(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> request) {
        
        Integer etape = request.get("etape");
        inscriptionService.markEtapeComplete(id, etape);
        
        return ResponseEntity.ok("Étape marquée comme complétée");
    }
}