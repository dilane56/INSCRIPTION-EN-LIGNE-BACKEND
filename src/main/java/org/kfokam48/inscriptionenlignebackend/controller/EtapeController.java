package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.etape.EtapeInscriptionDTO;
import org.kfokam48.inscriptionenlignebackend.service.EtapeInscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etapes")
@CrossOrigin("*")
public class EtapeController {
    
    private final EtapeInscriptionService etapeService;
    
    public EtapeController(EtapeInscriptionService etapeService) {
        this.etapeService = etapeService;
    }
    
    @GetMapping("/inscription/{inscriptionId}")
    public ResponseEntity<List<EtapeInscriptionDTO>> getEtapes(@PathVariable Long inscriptionId) {
        return ResponseEntity.ok(etapeService.getEtapesByInscription(inscriptionId));
    }
    
    @PostMapping("/completer/{inscriptionId}/{numeroEtape}")
    public ResponseEntity<EtapeInscriptionDTO> completerEtape(@PathVariable Long inscriptionId, @PathVariable Integer numeroEtape) {
        return ResponseEntity.ok(etapeService.completerEtape(inscriptionId, numeroEtape));
    }
    
    @GetMapping("/progression/{inscriptionId}")
    public ResponseEntity<Double> getProgression(@PathVariable Long inscriptionId) {
        return ResponseEntity.ok(etapeService.calculerPourcentageCompletion(inscriptionId));
    }
}