package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.transaction.Transactional;
import org.kfokam48.inscriptionenlignebackend.dto.inscription.InscriptionRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.inscription.InscriptionResponeDTO;
import org.kfokam48.inscriptionenlignebackend.enums.StatutInscription;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceNotFoundException;
import org.kfokam48.inscriptionenlignebackend.mapper.InscriptionMapper;
import org.kfokam48.inscriptionenlignebackend.model.EtapeInscription;
import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.kfokam48.inscriptionenlignebackend.repository.AnneeAcademiqueRepository;
import org.kfokam48.inscriptionenlignebackend.repository.CandidatRepository;
import org.kfokam48.inscriptionenlignebackend.repository.FormationRepository;
import org.kfokam48.inscriptionenlignebackend.repository.InscriptionRepository;
import org.kfokam48.inscriptionenlignebackend.service.InscriptionService;
import org.kfokam48.inscriptionenlignebackend.service.NotificationService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@Transactional
public class InscriptionServiceImpl  implements InscriptionService {
    private final InscriptionRepository inscriptionRepository;
    private final InscriptionMapper inscriptionMapper;
    private final CandidatRepository candidatRepository;
    private final AnneeAcademiqueRepository academiqueRepository;
    private final FormationRepository formationRepository;
    private final NotificationServiceImpl notificationService;

    public InscriptionServiceImpl(InscriptionRepository inscriptionRepository, InscriptionMapper inscriptionMapper, CandidatRepository candidatRepository, AnneeAcademiqueRepository academiqueRepository, FormationRepository formationRepository, NotificationServiceImpl notificationService) {
        this.inscriptionRepository = inscriptionRepository;
        this.inscriptionMapper = inscriptionMapper;
        this.candidatRepository = candidatRepository;
        this.academiqueRepository = academiqueRepository;
        this.formationRepository = formationRepository;
        this.notificationService = notificationService;
    }


    @Override
    public InscriptionResponeDTO findById(Long id) {
        Inscription inscription = inscriptionRepository.findById(id).orElseThrow(()->new RessourceNotFoundException("Inscription Not Found"));
        return inscriptionMapper.inscriptionToInscriptionResponeDTO(inscription);
    }

    @Override
    public InscriptionResponeDTO save(InscriptionRequestDTO inscriptionRequestDTO) {
        Inscription inscription = inscriptionMapper.inscriptionRequestDTOToInscription(inscriptionRequestDTO);
        inscription.setStatut(StatutInscription.BROUILLON);
        inscription.setDateCreation(LocalDateTime.now());
        inscription.setDerniereModification(LocalDateTime.now());
        inscription = inscriptionRepository.save(inscription);
        
        // Initialiser les 5 étapes
        initializerEtapes(inscription);
        
        return inscriptionMapper.inscriptionToInscriptionResponeDTO(inscription);
    }
    
    private void initializerEtapes(Inscription inscription) {
        String[] nomsEtapes = {
            "Informations Personnelles",
            "Documents Officiels", 
            "Parcours Académique",
            "Coordonnées",
            "Révision et Soumission"
        };
        
        for (int i = 0; i < nomsEtapes.length; i++) {
            EtapeInscription etape = new EtapeInscription();
            etape.setNumeroEtape(i + 1);
            etape.setNomEtape(nomsEtapes[i]);
            etape.setCompletee(false);
            etape.setInscription(inscription);
            inscription.getEtapes().add(etape);
        }
    }

    @Override
    public InscriptionResponeDTO update(InscriptionRequestDTO inscriptionRequestDTO, Long id) {
        Inscription inscription = inscriptionRepository.findById(id).orElseThrow(()->new RessourceNotFoundException("Inscription Not Found"));
        inscription.setFormation(formationRepository.findById(inscriptionRequestDTO.getFormationId()).orElseThrow(()->new RessourceNotFoundException("Formation Not Found")));
        inscription.setCandidat(candidatRepository.findById(inscriptionRequestDTO.getCandidatId()).orElseThrow(()->new RessourceNotFoundException("Candidat Not Found")));
        inscription.setAnneeAcademique(academiqueRepository.findById(inscriptionRequestDTO.getAnneeAcademiqueId()).orElseThrow(()->new RessourceNotFoundException("Annee Academique Not Found")));
        inscription = inscriptionRepository.save(inscription);
        return inscriptionMapper.inscriptionToInscriptionResponeDTO(inscription);
    }

    @Override
    public String delete(Long id) {
        Inscription inscription = inscriptionRepository.findById(id).orElseThrow(()->new RessourceNotFoundException("Inscription Not Found"));
        inscriptionRepository.delete(inscription);
        return "Inscription Deleted Successfully";
    }

    @Override
    public List<InscriptionResponeDTO> findAll() {
        return inscriptionMapper.inscriptionListToInscriptionResponseDTOList(inscriptionRepository.findAll());
    }

    @Override
    public List<InscriptionResponeDTO> getInscriptionsByCandidat(Long candidatId) {
        List<Inscription> inscriptions = inscriptionRepository.findByCandidatId(candidatId);
        return inscriptionMapper.inscriptionListToInscriptionResponseDTOList(inscriptions);
    }
    
    @Override
    public void updateEtapeActuelle(Long inscriptionId, Integer etape) {
        Inscription inscription = inscriptionRepository.findById(inscriptionId)
            .orElseThrow(() -> new RessourceNotFoundException("Inscription Not Found"));
        
        inscription.setEtapeActuelle(etape);
        inscription.setDerniereModification(LocalDateTime.now());
        
        // Marquer l'étape précédente comme complétée
        if (etape > 1) {
            inscription.getEtapes().stream()
                .filter(e -> e.getNumeroEtape() == etape - 1)
                .findFirst()
                .ifPresent(e -> {
                    e.setCompletee(true);
                    e.setDateCompletion(LocalDateTime.now());
                });
        }
        
        // Calculer le pourcentage de complétion
        long etapesCompletees = inscription.getEtapes().stream()
            .mapToLong(e -> e.getCompletee() ? 1 : 0)
            .sum();
        inscription.setPourcentageCompletion((double) etapesCompletees / 5 * 100);
        
        // Mettre à jour le statut si toutes les étapes sont complétées
        if (etapesCompletees == 5 && inscription.getStatut() == StatutInscription.BROUILLON) {
            inscription.setStatut(StatutInscription.SOUMISE);
            inscription.setDateSoumission(LocalDateTime.now());
        }
        
        inscriptionRepository.save(inscription);
    }
    
    @Override
    public List<InscriptionResponeDTO> getRecentInscriptions(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Inscription> inscriptions = inscriptionRepository.findAllByOrderByDateCreationDesc(pageable);
        return inscriptionMapper.inscriptionListToInscriptionResponseDTOList(inscriptions);
    }
    
    @Override
    public void updateStatut(Long inscriptionId, String statut, String commentaire) {
        Inscription inscription = inscriptionRepository.findById(inscriptionId)
            .orElseThrow(() -> new RessourceNotFoundException("Inscription Not Found"));
        
        inscription.setStatut(StatutInscription.valueOf(statut));
        inscription.setCommentaireAdmin(commentaire);
        inscription.setDerniereModification(LocalDateTime.now());
        
        if ("VALIDEE".equals(statut)) {
            inscription.setDateValidation(LocalDateTime.now());
        }
        
        inscriptionRepository.save(inscription);
        
        // Créer une notification pour le candidat
        createInscriptionStatusNotification(inscription, statut, commentaire);
    }
    
    private void createInscriptionStatusNotification(Inscription inscription, String statut, String commentaire) {
        Long candidatId = inscription.getCandidat().getId();
        
        if ("VALIDEE".equals(statut)) {
            notificationService.createNotification(
                candidatId,
                "Dossier validé !",
                "Félicitations ! Votre dossier d'inscription pour " + inscription.getFormation().getNomFormation() + 
                " a été validé." + 
                (commentaire != null && !commentaire.trim().isEmpty() ? " Commentaire: " + commentaire : ""),
                "DOSSIER_VALIDE"
            );
        } else if ("REJETEE".equals(statut)) {
            notificationService.createNotification(
                candidatId,
                "Dossier rejeté",
                "Votre dossier d'inscription pour " + inscription.getFormation().getNomFormation() + 
                " a été rejeté." +
                (commentaire != null && !commentaire.trim().isEmpty() ? " Motif: " + commentaire : ""),
                "DOSSIER_REJETE"
            );
        }
    }
    
    @Override
    public void markEtapeComplete(Long inscriptionId, Integer etape) {
        Inscription inscription = inscriptionRepository.findById(inscriptionId)
            .orElseThrow(() -> new RessourceNotFoundException("Inscription Not Found"));
        
        // Marquer l'étape comme complétée
        inscription.getEtapes().stream()
            .filter(e -> e.getNumeroEtape().equals(etape))
            .findFirst()
            .ifPresent(e -> {
                e.setCompletee(true);
                e.setDateCompletion(LocalDateTime.now());
            });
        
        // Recalculer la progression et le statut
        long etapesCompletees = inscription.getEtapes().stream()
            .mapToLong(e -> e.getCompletee() ? 1 : 0)
            .sum();
        inscription.setPourcentageCompletion((double) etapesCompletees / 5 * 100);
        
        // Mettre à jour le statut si toutes les étapes sont complétées
        if (etapesCompletees == 5 && inscription.getStatut() == StatutInscription.BROUILLON) {
            inscription.setStatut(StatutInscription.SOUMISE);
            inscription.setDateSoumission(LocalDateTime.now());
        }
        
        inscriptionRepository.save(inscription);
    }
}
