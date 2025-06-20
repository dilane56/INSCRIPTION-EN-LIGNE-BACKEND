package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.transaction.Transactional;
import org.kfokam48.inscriptionenlignebackend.dto.document.DocumentRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.document.DocumentResponseDTO;
import org.kfokam48.inscriptionenlignebackend.repository.DocumentRepository;
import org.kfokam48.inscriptionenlignebackend.service.DocumentService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public DocumentResponseDTO createDocument(DocumentRequestDTO documentRequestDTO) {
        return null;
    }

    @Override
    public DocumentResponseDTO updateDocument(DocumentRequestDTO documentRequestDTO, Long id) {
        return null;
    }

    @Override
    public DocumentResponseDTO getDocument(Long id) {

        return null;
    }

    @Override
    public DocumentResponseDTO deleteDocument(Long id) {
        return null;
    }

    @Override
    public List<DocumentResponseDTO> getAllDocuments() {
        return List.of();
    }
}
