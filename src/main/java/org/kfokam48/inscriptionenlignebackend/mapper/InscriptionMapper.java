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
import org.modelmapper.ModelMapper;

import java.util.List;

public class InscriptionMapper {

    private final ModelMapper modelMapper;
    private final CandidatRepository candidatRepository;
    private final AnneeAcademiqueRepository anneeAcademiqueRepository;
    private final FormationRepository formationRepository;

    public InscriptionMapper(ModelMapper modelMapper, CandidatRepository candidatRepository, AnneeAcademiqueRepository anneeAcademiqueRepository, FormationRepository formationRepository) {
        this.modelMapper = modelMapper;
        this.candidatRepository = candidatRepository;
        this.anneeAcademiqueRepository = anneeAcademiqueRepository;
        this.formationRepository = formationRepository;
    }
    public Inscription inscriptionRequestDTOToInscription(InscriptionRequestDTO inscriptionRequestDTO){
        Inscription inscription = new Inscription();
        inscription.setCandidat(candidatRepository.findById(inscriptionRequestDTO.getCandidatId()).orElseThrow(()-> new RessourceNotFoundException("Candidat not found")));
        inscription.setAnneeAcademique(anneeAcademiqueRepository.findById(inscriptionRequestDTO.getAnneeAcademiqueId()).orElseThrow(()->new RessourceNotFoundException("AnneeAcademique not found")));
        inscription.setFormation(formationRepository.findById(inscriptionRequestDTO.getFormationId()).orElseThrow(()->new RessourceNotFoundException("Formation not found")));
        return inscription;

    }

    public DocumentInInscription documentToDocumentInInscription(Document document){
        DocumentInInscription documentInInscription = new DocumentInInscription();
        documentInInscription.setId(document.getId());
        documentInInscription.setTypeDocument(document.getTypeDocument());
        documentInInscription.setCommentaire(document.getCommentaire());
        documentInInscription.setFormatValide(document.getFormatValide());
        documentInInscription.setValideParOCR(document.getValideParOCR());
        documentInInscription.setFichierUrl(document.getFichierUrl());
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
        inscriptionResponeDTO.setAnneeAcademique(inscription.getAnneeAcademique().getLibelle());
        inscriptionResponeDTO.setDateSoumission(inscription.getDateSoumission().toString());
        inscriptionResponeDTO.setEtat(inscription.getEtat());
        inscriptionResponeDTO.setDocuments(documentListToDocumentInInscriptionList(inscription.getDocuments()));
        return inscriptionResponeDTO;


    }

    public List<InscriptionResponeDTO> innscriptionListToInscriptionResponseDTOList(List<Inscription> inscriptions){
        return inscriptions.stream()
                .map(this::inscriptionToInscriptionResponeDTO)
                .toList();
    }
}
