package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.inscription.InscriptionRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.inscription.InscriptionResponeDTO;
import org.kfokam48.inscriptionenlignebackend.service.impl.InscriptionServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionController {
    private final InscriptionServiceImpl inscriptionService;

    public InscriptionController(InscriptionServiceImpl inscriptionService) {
        this.inscriptionService = inscriptionService;
    }


    @GetMapping
    public List<InscriptionResponeDTO> findAll() {
        return inscriptionService.findAll();
    }

    @GetMapping("/{id}")
    public InscriptionResponeDTO findById(@PathVariable Long id) {
        return inscriptionService.findById(id);
    }

    @PostMapping
    public InscriptionResponeDTO save(@RequestBody InscriptionRequestDTO dto) {
        return inscriptionService.save(dto);
    }

    @PutMapping("/{id}")
    public InscriptionResponeDTO update(@RequestBody InscriptionRequestDTO dto, @PathVariable Long id) {
        return inscriptionService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return inscriptionService.delete(id);
    }
}

