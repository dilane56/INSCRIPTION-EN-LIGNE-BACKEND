package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.anneAcademique.AnneeAcademiqueRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.anneAcademique.AnneeAcademiqueResponseDTO;
import org.kfokam48.inscriptionenlignebackend.service.impl.AnneAcademiqueServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annees")
public class AnneeAcademiqueController {
    private final AnneAcademiqueServiceImpl service;

    public AnneeAcademiqueController(AnneAcademiqueServiceImpl service) {
        this.service = service;
    }


    @GetMapping
    public List<AnneeAcademiqueResponseDTO> getAll() {
        return service.getAllAneeAcademique();
    }

    @GetMapping("/{id}")
    public AnneeAcademiqueResponseDTO getById(@PathVariable Long id) {
        return service.getAnneeById(id);
    }

    @PostMapping
    public AnneeAcademiqueResponseDTO create(@RequestBody AnneeAcademiqueRequestDTO dto) {
        return service.createAnne(dto);
    }

    @PutMapping("/{id}")
    public AnneeAcademiqueResponseDTO update(@RequestBody AnneeAcademiqueRequestDTO dto, @PathVariable Long id) {
        return service.updateAnnee(dto, id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return service.deleteAnnee(id);
    }
}