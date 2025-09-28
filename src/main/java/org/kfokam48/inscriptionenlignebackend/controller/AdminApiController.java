package org.kfokam48.inscriptionenlignebackend.controller;

import lombok.RequiredArgsConstructor;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatResponseDTO;
import org.kfokam48.inscriptionenlignebackend.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminApiController {

    private final AdminService adminService;

    @GetMapping("/candidats")
    public ResponseEntity<List<CandidatResponseDTO>> getAllCandidats() {
        return ResponseEntity.ok(adminService.getAllCandidatsForAdmin());
    }
}