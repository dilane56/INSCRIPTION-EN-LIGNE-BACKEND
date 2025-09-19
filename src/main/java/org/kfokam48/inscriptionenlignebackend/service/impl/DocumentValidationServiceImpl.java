package org.kfokam48.inscriptionenlignebackend.service.impl;

import org.kfokam48.inscriptionenlignebackend.model.Admin;
import org.kfokam48.inscriptionenlignebackend.model.Document;
import org.kfokam48.inscriptionenlignebackend.repository.AdminRepository;
import org.kfokam48.inscriptionenlignebackend.repository.DocumentRepository;
import org.kfokam48.inscriptionenlignebackend.service.DocumentValidationService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class DocumentValidationServiceImpl implements DocumentValidationService {
    
    private final DocumentRepository documentRepository;
    private final AdminRepository adminRepository;
    
    private static final List<String> FORMATS_PDF = Arrays.asList("application/pdf");
    private static final List<String> FORMATS_IMAGE = Arrays.asList("image/jpeg", "image/png", "image/jpg");
    private static final long TAILLE_MAX_MO = 5;
    
    public DocumentValidationServiceImpl(DocumentRepository documentRepository, AdminRepository adminRepository) {
        this.documentRepository = documentRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public boolean validerFormat(MultipartFile file, String typeAttendu) {
        String mimeType = file.getContentType();
        
        switch (typeAttendu.toUpperCase()) {
            case "PDF":
                return FORMATS_PDF.contains(mimeType);
            case "IMAGE":
                return FORMATS_IMAGE.contains(mimeType);
            default:
                return false;
        }
    }

    @Override
    public boolean detecterDoublon(MultipartFile file, Long candidatId) {
        String hash = calculerHash(file);
        return documentRepository.existsByHashDocumentAndInscription_Candidat_Id(hash, candidatId);
    }

    @Override
    public String calculerHash(MultipartFile file) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(file.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Erreur calcul hash", e);
        }
    }

    @Override
    public boolean validerTaille(MultipartFile file, long tailleMaxMo) {
        long tailleMaxBytes = tailleMaxMo * 1024 * 1024;
        return file.getSize() <= tailleMaxBytes;
    }

    @Override
    public void validerDocument(Long documentId, Long adminId, boolean valide, String commentaire) {
        Document document = documentRepository.findById(documentId).orElseThrow();
        Admin admin = adminRepository.findById(adminId).orElseThrow();
        
        document.setValide(valide);
        document.setCommentaireValidation(commentaire);
        document.setDateValidation(LocalDateTime.now());
        document.setAdminValidateur(admin);
        
        documentRepository.save(document);
    }
}