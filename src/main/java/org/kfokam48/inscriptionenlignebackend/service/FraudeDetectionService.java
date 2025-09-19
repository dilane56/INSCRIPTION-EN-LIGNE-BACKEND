package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.springframework.web.multipart.MultipartFile;

public interface FraudeDetectionService {
    boolean detecterDocumentSuspect(MultipartFile file);
    boolean detecterInscriptionSuspecte(Inscription inscription);
    void marquerCommeSuspect(Long inscriptionId, String raison);
    double calculerScoreRisque(Inscription inscription);
}