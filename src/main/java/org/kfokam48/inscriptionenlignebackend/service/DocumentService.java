package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.document.DocumentRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.document.DocumentResponseDTO;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.DocumentType;

import java.util.List;

public interface DocumentService {
    public DocumentResponseDTO createDocument(DocumentRequestDTO documentRequestDTO);
    public DocumentResponseDTO updateDocument(DocumentRequestDTO documentRequestDTO, Long id);
    public DocumentResponseDTO getDocument(Long id);
    public DocumentResponseDTO deleteDocument(Long id);
    public List<DocumentResponseDTO> getAllDocuments();
    void storeDocument(MultipartFile file, String type, Long inscriptionId);
}
