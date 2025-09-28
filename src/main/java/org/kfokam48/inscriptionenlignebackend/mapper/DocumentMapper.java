package org.kfokam48.inscriptionenlignebackend.mapper;

import org.kfokam48.inscriptionenlignebackend.dto.document.DocumentRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.document.DocumentResponseDTO;
import org.kfokam48.inscriptionenlignebackend.enums.TypeDocument;
import org.kfokam48.inscriptionenlignebackend.model.Document;
import org.kfokam48.inscriptionenlignebackend.repository.InscriptionRepository;
import org.springframework.stereotype.Component;
import org.kfokam48.inscriptionenlignebackend.model.Admin;

@Component
public class DocumentMapper {
    
    private final InscriptionRepository inscriptionRepository;
    
    public DocumentMapper(InscriptionRepository inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }
    
    public Document documentRequestDTOToDocument(DocumentRequestDTO dto) {
        Document document = new Document();
//        document.setNom(dto.getNom());
        document.setTypeDocument(TypeDocument.valueOf(dto.getTypeDocument()));
        if (dto.getInscriptionId() != null) {
            document.setInscription(inscriptionRepository.findById(dto.getInscriptionId()).orElse(null));
        }
        return document;
    }
    
    public DocumentResponseDTO documentToDocumentResponseDTO(Document document) {
        DocumentResponseDTO dto = new DocumentResponseDTO();
        dto.setId(document.getId());
        dto.setNom(document.getNom());
        dto.setNomOriginal(document.getNomOriginal());
        dto.setTypeDocument(document.getTypeDocument());
        dto.setDateUpload(document.getDateUpload());
        dto.setValide(document.getValide());
        dto.setCommentaireValidation(document.getCommentaireValidation());
        dto.setStatutValidation(document.getStatutValidation());
        dto.setMimeType(document.getMimeType());
        dto.setTaille(document.getTaille());
        dto.setDateValidation(document.getDateValidation());
        dto.setCandidatEmail(document.getInscription() != null && document.getInscription().getCandidat() != null ? document.getInscription().getCandidat().getEmail() : null);
        dto.setFormationName(document.getInscription() != null && document.getInscription().getFormation() != null ? document.getInscription().getFormation().getNomFormation() : null);
        // Récupérer l'admin validateur de façon défensive pour éviter NullPointerException
        Admin admin = document.getAdminValidateur();
        if (admin != null) {
            String first = admin.getFirstName() != null ? admin.getFirstName() : "";
            String last = admin.getLastName() != null ? admin.getLastName() : "";
            String fullName = (first + " " + last).trim();
            dto.setAdminValidateurNom(fullName.isEmpty() ? null : fullName);
        }

        if (document.getInscription() != null) {
            dto.setInscriptionId(document.getInscription().getId());
            if (document.getInscription().getCandidat() != null) {
                dto.setNomCandidat(document.getInscription().getCandidat().getFirstName());
                dto.setPrenomCandidat(document.getInscription().getCandidat().getLastName());
            }
        }
        dto.setCheminFichier(document.getCheminFichier());
        return dto;
    }
}