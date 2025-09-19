package org.kfokam48.inscriptionenlignebackend.service.impl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.kfokam48.inscriptionenlignebackend.repository.InscriptionRepository;
import org.kfokam48.inscriptionenlignebackend.service.ExportService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService {
    
    private final InscriptionRepository inscriptionRepository;
    
    public ExportServiceImpl(InscriptionRepository inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    @Override
    public ByteArrayResource exportInscriptionsExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Inscriptions");
            
            // En-têtes
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Candidat", "Email", "Formation", "Statut", "Date Création", "Date Soumission"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderStyle(workbook));
            }
            
            // Données
            List<Inscription> inscriptions = inscriptionRepository.findAll();
            int rowNum = 1;
            for (Inscription inscription : inscriptions) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(inscription.getId());
                row.createCell(1).setCellValue(inscription.getCandidat().getFirstName() + " " + inscription.getCandidat().getLastName());
                row.createCell(2).setCellValue(inscription.getCandidat().getEmail());
                row.createCell(3).setCellValue(inscription.getFormation().getNomFormation());
                row.createCell(4).setCellValue(inscription.getStatut().toString());
                row.createCell(5).setCellValue(inscription.getDateCreation().toString());
                row.createCell(6).setCellValue(inscription.getDateSoumission() != null ? inscription.getDateSoumission().toString() : "");
            }
            
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
            
        } catch (IOException e) {
            throw new RuntimeException("Erreur export Excel", e);
        }
    }

    @Override
    public ByteArrayResource exportInscriptionsCSV() {
        StringBuilder csv = new StringBuilder();
        csv.append("ID,Candidat,Email,Formation,Statut,Date Création,Date Soumission\n");
        
        List<Inscription> inscriptions = inscriptionRepository.findAll();
        for (Inscription inscription : inscriptions) {
            csv.append(inscription.getId()).append(",")
               .append(inscription.getCandidat().getFirstName()).append(" ").append(inscription.getCandidat().getLastName()).append(",")
               .append(inscription.getCandidat().getEmail()).append(",")
               .append(inscription.getFormation().getNomFormation()).append(",")
               .append(inscription.getStatut()).append(",")
               .append(inscription.getDateCreation()).append(",")
               .append(inscription.getDateSoumission() != null ? inscription.getDateSoumission() : "").append("\n");
        }
        
        return new ByteArrayResource(csv.toString().getBytes());
    }

    @Override
    public ByteArrayResource exportStatistiquesRapport() {
        // Implémentation simplifiée - peut être étendue
        StringBuilder rapport = new StringBuilder();
        rapport.append("RAPPORT STATISTIQUES INSCRIPTIONS\n");
        rapport.append("=================================\n\n");
        
        long total = inscriptionRepository.count();
        rapport.append("Total inscriptions: ").append(total).append("\n");
        
        return new ByteArrayResource(rapport.toString().getBytes());
    }
    
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }
}