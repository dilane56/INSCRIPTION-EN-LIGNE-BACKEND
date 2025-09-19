package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatResponseDTO;
import org.kfokam48.inscriptionenlignebackend.dto.inscription.InscriptionResponeDTO;
import org.kfokam48.inscriptionenlignebackend.model.User;
import org.kfokam48.inscriptionenlignebackend.service.CandidatService;
import org.kfokam48.inscriptionenlignebackend.service.InscriptionService;
import org.kfokam48.inscriptionenlignebackend.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidats")
public class CandidatController {
    
    private final CandidatService candidatService;
    private final InscriptionService inscriptionService;
    private final AuthService authService;
    
    public CandidatController(CandidatService candidatService, 
                             InscriptionService inscriptionService,
                             AuthService authService) {
        this.candidatService = candidatService;
        this.inscriptionService = inscriptionService;
        this.authService = authService;
    }
    
    @PostMapping
    public ResponseEntity<CandidatResponseDTO> createCandidat(@RequestBody CandidatRequestDTO candidatRequestDTO) {
        CandidatResponseDTO candidat = candidatService.createCandidat(candidatRequestDTO);
        return ResponseEntity.ok(candidat);
    }
    
    @GetMapping("/profile")
    public ResponseEntity<CandidatResponseDTO> getProfile(Authentication authentication) {
        String email = authentication.getName();
        User user = authService.getUserByEmail(email);
        CandidatResponseDTO candidat = candidatService.getCandidatById(user.getId());
        return ResponseEntity.ok(candidat);
    }
    
    @GetMapping("/inscriptions")
    public ResponseEntity<List<InscriptionResponeDTO>> getMyInscriptions(Authentication authentication) {
        String email = authentication.getName();
        User user = authService.getUserByEmail(email);
        List<InscriptionResponeDTO> inscriptions = inscriptionService.getInscriptionsByCandidat(user.getId());
        return ResponseEntity.ok(inscriptions);
    }
}