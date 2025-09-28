package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.service.ExportService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/export")
@CrossOrigin("*")
@PreAuthorize("hasRole('ADMIN')")
public class ExportController {
    
    private final ExportService exportService;
    
    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }
    
    @GetMapping("/inscriptions/excel")
    public ResponseEntity<ByteArrayResource> exportExcel() {
        ByteArrayResource resource = exportService.exportInscriptionsExcel();
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=inscriptions.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
    
    @GetMapping("/inscriptions/csv")
    public ResponseEntity<ByteArrayResource> exportCSV() {
        ByteArrayResource resource = exportService.exportInscriptionsCSV();
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=inscriptions.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }
    
    @GetMapping("/statistiques/rapport")
    public ResponseEntity<ByteArrayResource> exportStatistiquesRapport() {
        ByteArrayResource resource = exportService.exportStatistiquesRapport();
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rapport-statistiques.txt")
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }
}