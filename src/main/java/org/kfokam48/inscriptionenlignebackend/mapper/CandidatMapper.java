package org.kfokam48.inscriptionenlignebackend.mapper;

import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatResponseDTO;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.InscriptionInCandidatDTO;
import org.kfokam48.inscriptionenlignebackend.model.Candidat;
import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CandidatMapper {
    private final ModelMapper modelMapper;

    public CandidatMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Candidat candidatRequestDTOToCandidat(CandidatRequestDTO candidatRequestDTO){
        return modelMapper.map(candidatRequestDTO, Candidat.class);
    }

    public InscriptionInCandidatDTO inscriptionToInscriptionInCandidatDTO(Inscription inscription){
        InscriptionInCandidatDTO inscriptionInCandidatDTO = new InscriptionInCandidatDTO();
        inscriptionInCandidatDTO.setId(inscription.getId());
        inscriptionInCandidatDTO.setFormationName(inscription.getFormation().getNomFormation());
        inscriptionInCandidatDTO.setAnneeAcademique(inscription.getAnneeAcademique().getLibelle());
        inscriptionInCandidatDTO.setDateSoumission(inscription.getDateSoumission());
        inscriptionInCandidatDTO.setEtat(inscription.getEtat());
        return inscriptionInCandidatDTO;

    }

    public List<InscriptionInCandidatDTO> inscripttionListToInscriptionInCandidatDTOlist(List<Inscription> inscriptions){
        return inscriptions.stream()
                .map(this::inscriptionToInscriptionInCandidatDTO)
                .toList();
    }

    public CandidatResponseDTO candidatToCandidatResponseDTO(Candidat candidat){
        CandidatResponseDTO candidatResponseDTO = new CandidatResponseDTO();
        candidatResponseDTO.setId(candidat.getId());
        candidatResponseDTO.setFirstName(candidat.getFirstName());
        candidatResponseDTO.setLastName(candidat.getLastName());
        candidatResponseDTO.setEmail(candidat.getEmail());
        candidatResponseDTO.setPhone(candidat.getPhone());
        candidatResponseDTO.setDateNaissance(candidat.getDateNaissance());
        candidatResponseDTO.setRole(candidat.getRole());
        candidatResponseDTO.setInscriptions(inscripttionListToInscriptionInCandidatDTOlist(candidat.getInscriptions()));
        return candidatResponseDTO;
    }

    public List<CandidatResponseDTO> candidatListToCandidatResponseDTOList(List<Candidat> candidats){
        return candidats.stream()
                .map(this::candidatToCandidatResponseDTO)
                .toList();
    }
}
