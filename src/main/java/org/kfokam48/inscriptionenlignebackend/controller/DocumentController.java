package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.document.DocumentResponseDTO;
import org.kfokam48.inscriptionenlignebackend.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    
    private final DocumentService documentService;
    
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }
    
    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type,
            @RequestParam("inscriptionId") Long inscriptionId) {
        
        try {
            documentService.storeDocument(file, type, inscriptionId);
            return ResponseEntity.ok("Document uploadé avec succès");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'upload: " + e.getMessage());
        }
    }
    
    @GetMapping("/inscription/{inscriptionId}")
    public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByInscription(@PathVariable Long inscriptionId) {
        // TODO: Implémenter la récupération des documents par inscription
        return ResponseEntity.ok(List.of());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponseDTO> getDocument(@PathVariable Long id) {
        DocumentResponseDTO document = documentService.getDocument(id);
        return ResponseEntity.ok(document);
    }
}