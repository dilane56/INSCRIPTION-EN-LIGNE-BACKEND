package org.kfokam48.inscriptionenlignebackend.service;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentValidationService {
    boolean validerFormat(MultipartFile file, String typeAttendu);
    boolean detecterDoublon(MultipartFile file, Long candidatId);
    String calculerHash(MultipartFile file);
    boolean validerTaille(MultipartFile file, long tailleMaxMo);
    void validerDocument(Long documentId, Long adminId, boolean valide, String commentaire);
}