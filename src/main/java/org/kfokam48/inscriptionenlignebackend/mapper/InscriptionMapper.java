package org.kfokam48.inscriptionenlignebackend.mapper;

import org.kfokam48.inscriptionenlignebackend.dto.inscription.DocumentInInscription;
import org.kfokam48.inscriptionenlignebackend.dto.inscription.InscriptionRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.inscription.InscriptionResponeDTO;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceNotFoundException;
import org.kfokam48.inscriptionenlignebackend.model.Document;
import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.kfokam48.inscriptionenlignebackend.repository.AnneeAcademiqueRepository;
import org.kfokam48.inscriptionenlignebackend.repository.CandidatRepository;
import org.kfokam48.inscriptionenlignebackend.repository.FormationRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InscriptionMapper {

    private final CandidatRepository candidatRepository;
    private final FormationRepository formationRepository;
    private final AnneeAcademiqueRepository anneeAcademiqueRepository;

    public InscriptionMapper(CandidatRepository candidatRepository,
                           FormationRepository formationRepository, AnneeAcademiqueRepository anneeAcademiqueRepository) {
        this.candidatRepository = candidatRepository;
        this.formationRepository = formationRepository;
        this.anneeAcademiqueRepository = anneeAcademiqueRepository;
    }
    
    public Inscription inscriptionRequestDTOToInscription(InscriptionRequestDTO inscriptionRequestDTO){
        Inscription inscription = new Inscription();
        inscription.setCandidat(candidatRepository.findById(inscriptionRequestDTO.getCandidatId())
                .orElseThrow(() -> new RessourceNotFoundException("Candidat not found")));
        inscription.setAnneeAcademique(anneeAcademiqueRepository.findById(inscriptionRequestDTO.getAnneeAcademiqueId())
                .orElseThrow(() -> new RessourceNotFoundException("Annee academique not found")));
        inscription.setFormation(formationRepository.findById(inscriptionRequestDTO.getFormationId())
                .orElseThrow(() -> new RessourceNotFoundException("Formation not found")));
        
        // Initialiser la progression : étape 1 en cours
        inscription.setEtapeActuelle(1);
        inscription.setPourcentageCompletion(0.0);
        
        return inscription;
    }

    public DocumentInInscription documentToDocumentInInscription(Document document){
        DocumentInInscription documentInInscription = new DocumentInInscription();
        documentInInscription.setId(document.getId());
        documentInInscription.setTypeDocument(String.valueOf(document.getTypeDocument()));
        documentInInscription.setCommentaire(document.getCommentaireValidation());
        documentInInscription.setNom(document.getNom());
        documentInInscription.setValide(document.getValide());
        return documentInInscription;
    }
    
    public List<DocumentInInscription> documentListToDocumentInInscriptionList(List<Document> documents){
        return documents.stream()
                .map(this::documentToDocumentInInscription)
                .toList();
    }
    
    public InscriptionResponeDTO inscriptionToInscriptionResponeDTO(Inscription inscription){
        InscriptionResponeDTO inscriptionResponeDTO = new InscriptionResponeDTO();
        inscriptionResponeDTO.setId(inscription.getId());
        inscriptionResponeDTO.setCandidatEmail(inscription.getCandidat().getEmail());
        inscriptionResponeDTO.setFormationName(inscription.getFormation().getNomFormation());
        inscriptionResponeDTO.setFormationId(inscription.getFormation().getId());
        inscriptionResponeDTO.setAnneeAcademique(inscription.getAnneeAcademique().getLibelle());
        inscriptionResponeDTO.setAnneeAcademiqueId(inscription.getAnneeAcademique().getId());
        inscriptionResponeDTO.setDateSoumission(inscription.getDateSoumission());
        inscriptionResponeDTO.setEtapeActuelle(inscription.getEtapeActuelle());
        inscriptionResponeDTO.setPourcentageCompletion(inscription.getPourcentageCompletion());
        inscriptionResponeDTO.setStatut(inscription.getStatut());
        inscriptionResponeDTO.setCommentaireAdmin(inscription.getCommentaireAdmin());
        inscriptionResponeDTO.setCandidatNom(inscription.getCandidat().getFirstName());
        inscriptionResponeDTO.setCandidatPrenom(inscription.getCandidat().getLastName());
        
        // Gestion des valeurs nulles pour adminValidateur
        if (inscription.getAdminValidateur() != null) {
            inscriptionResponeDTO.setAdminValidateurNom(inscription.getAdminValidateur().getFirstName() + " " + 
                    inscription.getAdminValidateur().getLastName());
        }
        
        inscriptionResponeDTO.setDateValidation(inscription.getDateValidation());
        inscriptionResponeDTO.setDateCreation(inscription.getDateCreation());
        inscriptionResponeDTO.setDerniereModification(inscription.getDerniereModification());
        inscriptionResponeDTO.setDocuments(documentListToDocumentInInscriptionList(inscription.getDocuments()));

        // --- Ajout du mapping des coordonnées du candidat ---
        if (inscription.getCandidat() != null) {
            inscriptionResponeDTO.setAdresse(inscription.getCandidat().getAdresse());
            inscriptionResponeDTO.setVille(inscription.getCandidat().getVille());
            inscriptionResponeDTO.setCodePostal(inscription.getCandidat().getCodePostal());
            inscriptionResponeDTO.setPays(inscription.getCandidat().getPays());
            inscriptionResponeDTO.setContactPourUrgence(inscription.getCandidat().getContactPourUrgence());
            inscriptionResponeDTO.setTelephoneUrgence(inscription.getCandidat().getTelephoneUrgence());
        }

        return inscriptionResponeDTO;
    }

    public List<InscriptionResponeDTO> inscriptionListToInscriptionResponseDTOList(List<Inscription> inscriptions){
        return inscriptions.stream()
                .map(this::inscriptionToInscriptionResponeDTO)
                .toList();
    }
}