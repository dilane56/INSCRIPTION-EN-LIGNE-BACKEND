package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.transaction.Transactional;
import org.kfokam48.inscriptionenlignebackend.dto.document.DocumentRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.document.DocumentResponseDTO;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceNotFoundException;
import org.kfokam48.inscriptionenlignebackend.model.Document;
import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.kfokam48.inscriptionenlignebackend.repository.DocumentRepository;
import org.kfokam48.inscriptionenlignebackend.repository.InscriptionRepository;
import org.kfokam48.inscriptionenlignebackend.service.DocumentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final InscriptionRepository inscriptionRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository, InscriptionRepository inscriptionRepository) {
        this.documentRepository = documentRepository;
        this.inscriptionRepository = inscriptionRepository;
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

    @Override
    public void storeDocument(MultipartFile file, String type, Long inscriptionId) {
        try {
            Inscription inscription = inscriptionRepository.findById(inscriptionId)
                    .orElseThrow(() -> new RessourceNotFoundException("Inscription not found"));

            Document doc = new Document();
            doc.setNom(file.getOriginalFilename());
            doc.setType(type);
            doc.setData(file.getBytes());
            doc.setInscription(inscription);

            documentRepository.save(doc);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

}
