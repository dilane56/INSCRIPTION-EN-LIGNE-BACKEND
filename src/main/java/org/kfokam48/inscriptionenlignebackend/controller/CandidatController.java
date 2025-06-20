package org.kfokam48.inscriptionenlignebackend.controller;


import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatResponseDTO;
import org.kfokam48.inscriptionenlignebackend.service.impl.CandidatServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidats")
public class CandidatController {
    private final CandidatServiceImpl candidatService;

    public CandidatController(CandidatServiceImpl candidatService) {
        this.candidatService = candidatService;
    }


    @GetMapping
    public List<CandidatResponseDTO> getAllCandidats() {
        return candidatService.getAllCandidats();
    }

    @GetMapping("/{id}")
    public CandidatResponseDTO getCandidatById(@PathVariable Long id) {
        return candidatService.getCandidatById(id);
    }

    @PostMapping
    public CandidatResponseDTO createCandidat(@RequestBody CandidatRequestDTO dto) {
        return candidatService.createCandidat(dto);
    }

    @PutMapping("/{id}")
    public CandidatResponseDTO updateCandidat(@RequestBody CandidatRequestDTO dto, @PathVariable Long id) {
        return candidatService.updateCandidat(dto, id);
    }

    @DeleteMapping("/{id}")
    public String deleteCandidat(@PathVariable Long id) {
        return candidatService.deleteCandidat(id);
    }
}
