package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.niveau.NiveauRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.niveau.NiveauResponseDTO;
import org.kfokam48.inscriptionenlignebackend.service.impl.NiveauServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/niveaux")
public class NiveauController {

    private final NiveauServiceImpl niveauService;

    public NiveauController(NiveauServiceImpl niveauService) {
        this.niveauService = niveauService;
    }


    @PostMapping
    public ResponseEntity<NiveauResponseDTO> create(@RequestBody NiveauRequestDTO dto) {
        return ResponseEntity.ok(niveauService.createNiveau(dto));
    }

    @GetMapping
    public ResponseEntity<List<NiveauResponseDTO>> all() {
        return ResponseEntity.ok(niveauService.getAllNiveaux());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NiveauResponseDTO> update(@PathVariable Long id, @RequestBody NiveauRequestDTO dto) {
        return ResponseEntity.ok(niveauService.updateNiveau(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(niveauService.deleteNiveau(id));
    }
}

