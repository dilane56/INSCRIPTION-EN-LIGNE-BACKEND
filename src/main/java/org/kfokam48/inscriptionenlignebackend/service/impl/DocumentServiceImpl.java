package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.transaction.Transactional;
import org.kfokam48.inscriptionenlignebackend.dto.document.DocumentRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.document.DocumentResponseDTO;
import org.kfokam48.inscriptionenlignebackend.enums.TypeDocument;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceNotFoundException;
import org.kfokam48.inscriptionenlignebackend.mapper.DocumentMapper;
import org.kfokam48.inscriptionenlignebackend.model.Document;
import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.kfokam48.inscriptionenlignebackend.repository.DocumentRepository;
import org.kfokam48.inscriptionenlignebackend.repository.InscriptionRepository;
import org.kfokam48.inscriptionenlignebackend.service.DocumentService;
import org.kfokam48.inscriptionenlignebackend.minIo.MinIOService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final InscriptionRepository inscriptionRepository;
    private final DocumentMapper documentMapper;
    private final MinIOService minioService;

    public DocumentServiceImpl(DocumentRepository documentRepository, 
                              InscriptionRepository inscriptionRepository, 
                              DocumentMapper documentMapper,
                              MinIOService minioService) {
        this.documentRepository = documentRepository;
        this.inscriptionRepository = inscriptionRepository;
        this.documentMapper = documentMapper;
        this.minioService = minioService;
    }

    @Override
    public DocumentResponseDTO createDocument(DocumentRequestDTO documentRequestDTO) {
        Document document = documentMapper.documentRequestDTOToDocument(documentRequestDTO);
        document.setDateUpload(LocalDateTime.now());
        document = documentRepository.save(document);
        return documentMapper.documentToDocumentResponseDTO(document);
    }

    @Override
    public DocumentResponseDTO updateDocument(DocumentRequestDTO documentRequestDTO, Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Document not found"));
        document.setTypeDocument(TypeDocument.valueOf(documentRequestDTO.getTypeDocument()));
        document = documentRepository.save(document);
        return documentMapper.documentToDocumentResponseDTO(document);
    }

    @Override
    public DocumentResponseDTO getDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Document not found"));
        return documentMapper.documentToDocumentResponseDTO(document);
    }

    @Override
    public DocumentResponseDTO deleteDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Document not found"));
        documentRepository.delete(document);
        return documentMapper.documentToDocumentResponseDTO(document);
    }

    @Override
    public List<DocumentResponseDTO> getAllDocuments() {
        return documentRepository.findAll().stream()
                .map(documentMapper::documentToDocumentResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void storeDocument(MultipartFile file, String type, Long inscriptionId) {
        try {
            Inscription inscription = inscriptionRepository.findById(inscriptionId)
                    .orElseThrow(() -> new RessourceNotFoundException("Inscription not found"));

            // Upload vers MinIO
            String minioUrl = minioService.uploadFile(file);
            
            Document doc = new Document();
            doc.setNom(file.getOriginalFilename());
            doc.setNomOriginal(file.getOriginalFilename());
            doc.setTypeDocument(TypeDocument.valueOf(type.toUpperCase()));
            doc.setMimeType(file.getContentType());
            doc.setTaille(file.getSize());
            doc.setDateUpload(LocalDateTime.now());
            doc.setCheminFichier(minioUrl); // URL MinIO
            doc.setInscription(inscription);

            documentRepository.save(doc);
            
            // Mettre à jour la progression
            updateInscriptionProgress(inscription);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to store document: " + e.getMessage(), e);
        }
    }
    
    private void updateInscriptionProgress(Inscription inscription) {
        long docCount = inscription.getDocuments().size() + 1; // +1 pour le document en cours
        inscription.setEtapeActuelle(Math.max(inscription.getEtapeActuelle(), 2));
        
        // Progression basée sur le nombre de documents (minimum 40% à l'étape 2)
        double baseProgress = 20.0; // Étape 1 complétée
        double docProgress = Math.min(20.0, docCount * 5.0); // Max 20% pour les documents
        inscription.setPourcentageCompletion(baseProgress + docProgress);
        
        inscriptionRepository.save(inscription);
    }

}
