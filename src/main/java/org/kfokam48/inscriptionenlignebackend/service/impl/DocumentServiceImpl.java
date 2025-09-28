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
    private final NotificationServiceImpl notificationService;

    public DocumentServiceImpl(DocumentRepository documentRepository, InscriptionRepository inscriptionRepository, DocumentMapper documentMapper, MinIOService minioService, NotificationServiceImpl notificationService) {
        this.documentRepository = documentRepository;
        this.inscriptionRepository = inscriptionRepository;
        this.documentMapper = documentMapper;
        this.minioService = minioService;
        this.notificationService = notificationService;
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

            TypeDocument typeDocument = TypeDocument.valueOf(type.toUpperCase());
            
            // Vérifier si un document de ce type existe déjà
            Document existingDoc = documentRepository.findByInscriptionAndTypeDocument(inscription, typeDocument)
                    .orElse(null);
            
            // Upload vers MinIO
            String minioUrl = minioService.uploadFile(file);
            
            if (existingDoc != null) {
                // Mettre à jour le document existant
                existingDoc.setNom(file.getOriginalFilename());
                existingDoc.setNomOriginal(file.getOriginalFilename());
                existingDoc.setMimeType(file.getContentType());
                existingDoc.setTaille(file.getSize());
                existingDoc.setDateUpload(LocalDateTime.now());
                existingDoc.setCheminFichier(minioUrl);
                documentRepository.save(existingDoc);
            } else {
                // Créer un nouveau document
                Document doc = new Document();
                doc.setNom(file.getOriginalFilename());
                doc.setNomOriginal(file.getOriginalFilename());
                doc.setTypeDocument(typeDocument);
                doc.setMimeType(file.getContentType());
                doc.setTaille(file.getSize());
                doc.setDateUpload(LocalDateTime.now());
                doc.setCheminFichier(minioUrl);
                doc.setInscription(inscription);
                documentRepository.save(doc);
            }
            
            // Mettre à jour la progression
            updateInscriptionProgress(inscription);
            
            // Vérifier si tous les documents obligatoires sont présents
            checkAndCompleteDocumentStep(inscription);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to store document: " + e.getMessage(), e);
        }
    }
    
    private void updateInscriptionProgress(Inscription inscription) {
        // Recharger l'inscription pour avoir les documents à jour
        inscription = inscriptionRepository.findById(inscription.getId()).orElse(inscription);
        
        long docCount = inscription.getDocuments().size();
        inscription.setEtapeActuelle(Math.max(inscription.getEtapeActuelle(), 2));
        
        // Progression basée sur le nombre de documents uniques (max 4 documents obligatoires)
        double baseProgress = 20.0; // Étape 1 complétée
        double docProgress = Math.min(20.0, (docCount / 4.0) * 20.0); // Max 20% pour 4 documents
        inscription.setPourcentageCompletion(baseProgress + docProgress);
        
        inscriptionRepository.save(inscription);
    }
    
    private void checkAndCompleteDocumentStep(Inscription inscription) {
        // Vérifier si les 4 documents obligatoires sont présents
        long documentsObligatoires = inscription.getDocuments().stream()
            .filter(doc -> doc.getTypeDocument().name().matches("CNI_RECTO|CNI_VERSO|PASSEPORT|ACTE_NAISSANCE"))
            .count();
        
        if (documentsObligatoires >= 4) {
            // Marquer l'étape 2 comme complétée
            inscription.getEtapes().stream()
                .filter(e -> e.getNumeroEtape() == 2)
                .findFirst()
                .ifPresent(e -> {
                    if (!e.getCompletee()) {
                        e.setCompletee(true);
                        e.setDateCompletion(LocalDateTime.now());
                        inscriptionRepository.save(inscription);
                    }
                });
        }
    }
    
    @Override
    public List<DocumentResponseDTO> getPendingDocuments() {
        List<Document> documents = documentRepository.findByStatutValidationIsNull();
        return documents.stream()
                .map(documentMapper::documentToDocumentResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public void validateDocument(Long id, String statut, String commentaire) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Document not found"));
        
        document.setStatutValidation(statut);
        document.setCommentaireValidation(commentaire);
        document.setDateValidation(LocalDateTime.now());
        
        documentRepository.save(document);
        
        // Créer une notification pour le candidat
        createDocumentValidationNotification(document, statut, commentaire);
    }
    
    private void createDocumentValidationNotification(Document document, String statut, String commentaire) {
        Long candidatId = document.getInscription().getCandidat().getId();
        String typeDoc = getTypeDocumentLabel(document.getTypeDocument().name());
        
        if ("APPROUVE".equals(statut)) {
            notificationService.createNotification(
                candidatId,
                "Document approuvé",
                "Votre document " + typeDoc + " a été approuvé." + 
                (commentaire != null && !commentaire.trim().isEmpty() ? " Commentaire: " + commentaire : ""),
                "DOCUMENT_VALIDE"
            );
        } else if ("REJETE".equals(statut)) {
            notificationService.createNotification(
                candidatId,
                "Document rejeté",
                "Votre document " + typeDoc + " a été rejeté. Veuillez le corriger et le re-uploader." +
                (commentaire != null && !commentaire.trim().isEmpty() ? " Motif: " + commentaire : ""),
                "DOCUMENT_REJETE"
            );
        }
    }
    
    private String getTypeDocumentLabel(String type) {
        return switch (type) {
            case "CNI_RECTO" -> "CNI Recto";
            case "CNI_VERSO" -> "CNI Verso";
            case "PASSEPORT" -> "Passeport";
            case "ACTE_NAISSANCE" -> "Acte de naissance";
            case "DIPLOME" -> "Diplôme";
            case "RELEVE_NOTES" -> "Relevé de notes";
            case "PHOTO_IDENTITE" -> "Photo d'identité";
            default -> type;
        };
    }

}
