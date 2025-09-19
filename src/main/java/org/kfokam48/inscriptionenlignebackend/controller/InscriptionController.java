package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.inscription.InscriptionRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.inscription.InscriptionResponeDTO;
import org.kfokam48.inscriptionenlignebackend.model.User;
import org.kfokam48.inscriptionenlignebackend.service.InscriptionService;
import org.kfokam48.inscriptionenlignebackend.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionController {
    
    private final InscriptionService inscriptionService;
    private final AuthService authService;
    
    public InscriptionController(InscriptionService inscriptionService, AuthService authService) {
        this.inscriptionService = inscriptionService;
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
}