package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.service.impl.DocumentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentServiceImpl documentService;

    public DocumentController(DocumentServiceImpl documentService) {
        this.documentService = documentService;
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type,
            @RequestParam("inscriptionId") Long inscriptionId) {
        documentService.storeDocument(file, type, inscriptionId);
        return ResponseEntity.ok("Document uploaded successfully.");
    }
}

