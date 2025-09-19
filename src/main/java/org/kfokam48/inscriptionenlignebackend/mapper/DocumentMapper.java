package org.kfokam48.inscriptionenlignebackend.mapper;

import org.kfokam48.inscriptionenlignebackend.dto.document.DocumentRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.document.DocumentResponseDTO;
import org.kfokam48.inscriptionenlignebackend.enums.TypeDocument;
import org.kfokam48.inscriptionenlignebackend.model.Document;
import org.kfokam48.inscriptionenlignebackend.repository.InscriptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {
    
    private final ModelMapper modelMapper;
    private final InscriptionRepository inscriptionRepository;
    
    public DocumentMapper(ModelMapper modelMapper, InscriptionRepository inscriptionRepository) {
        this.modelMapper = modelMapper;
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
        dto.setTypeDocument(document.getTypeDocument());
        dto.setDateUpload(document.getDateUpload());
        dto.setValide(document.getValide());
        dto.setCommentaireValidation(document.getCommentaireValidation());
        if (document.getInscription() != null) {
            dto.setInscriptionId(document.getInscription().getId());
        }
        return dto;
    }
}