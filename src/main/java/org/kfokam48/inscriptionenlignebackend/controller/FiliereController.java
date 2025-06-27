package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.filiere.FiliereRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.filiere.FiliereResponseDTO;
import org.kfokam48.inscriptionenlignebackend.service.impl.FiliereServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annees")
public class FiliereController {
    private final FiliereServiceImpl service;

    public FiliereController(FiliereServiceImpl service) {
        this.service = service;
    }


    @GetMapping
    public List<FiliereResponseDTO> getAll() {
        return service.getAllAneeAcademique();
    }

    @GetMapping("/{id}")
    public FiliereResponseDTO getById(@PathVariable Long id) {
        return service.getAnneeById(id);
    }

    @PostMapping
    public FiliereResponseDTO create(@RequestBody FiliereRequestDTO dto) {
        return service.createAnne(dto);
    }

    @PutMapping("/{id}")
    public FiliereResponseDTO update(@RequestBody FiliereRequestDTO dto, @PathVariable Long id) {
        return service.updateAnnee(dto, id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return service.deleteAnnee(id);
    }
}