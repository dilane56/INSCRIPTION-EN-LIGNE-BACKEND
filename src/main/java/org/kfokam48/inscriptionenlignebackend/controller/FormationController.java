package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.formation.FormationRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.formation.FormationResponseDTO;
import org.kfokam48.inscriptionenlignebackend.service.impl.FormationServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formations")
public class FormationController {
    private final FormationServiceImpl formationService;

    public FormationController(FormationServiceImpl formationService) {
        this.formationService = formationService;
    }


    @GetMapping
    public List<FormationResponseDTO> getAllFormations() {
        return formationService.getAllFormations();
    }

    @GetMapping("/{id}")
    public FormationResponseDTO getFormationById(@PathVariable Long id) {
        return formationService.getFormationById(id);
    }

    @PostMapping
    public FormationResponseDTO createFormation(@RequestBody FormationRequestDTO dto) {
        return formationService.createFormation(dto);
    }

    @PutMapping("/{id}")
    public FormationResponseDTO updateFormation(@RequestBody FormationRequestDTO dto, @PathVariable Long id) {
        return formationService.updateFormation(dto, id);
    }

    @DeleteMapping("/{id}")
    public String deleteFormation(@PathVariable Long id) {
        return formationService.deleteFormation(id);
    }
}

