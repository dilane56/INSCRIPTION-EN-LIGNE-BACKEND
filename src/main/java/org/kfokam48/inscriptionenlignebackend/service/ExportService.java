package org.kfokam48.inscriptionenlignebackend.service;

import org.springframework.core.io.ByteArrayResource;

public interface ExportService {
    ByteArrayResource exportInscriptionsExcel();
    ByteArrayResource exportInscriptionsCSV();
    ByteArrayResource exportStatistiquesRapport();
}