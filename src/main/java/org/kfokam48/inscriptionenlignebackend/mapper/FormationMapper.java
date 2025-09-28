package org.kfokam48.inscriptionenlignebackend.mapper;

import org.kfokam48.inscriptionenlignebackend.dto.filiere.FiliereDTO;
import org.kfokam48.inscriptionenlignebackend.dto.formation.FormationRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.formation.FormationResponseDTO;
import org.kfokam48.inscriptionenlignebackend.dto.formation.InscriptionInFormationDTO;
import org.kfokam48.inscriptionenlignebackend.model.Filiere;
import org.kfokam48.inscriptionenlignebackend.model.Formation;
import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class FormationMapper {
    private final ModelMapper modelMapper;

    public FormationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public Formation formationRequestDTOToFormation(FormationRequestDTO formationRequestDTO) {
        return modelMapper.map(formationRequestDTO, Formation.class);
    }

    public FormationResponseDTO formationToFormationResponseDTO(Formation formation) {
        FormationResponseDTO formationResponseDTO = new FormationResponseDTO();
        formationResponseDTO.setId(formation.getId());
        formationResponseDTO.setNomFormation(formation.getNomFormation());
        formationResponseDTO.setEtablissement(formation.getEtablissement());
        formationResponseDTO.setFiliere(filiereToFiliereDTO(formation.getFiliere()));
        formationResponseDTO.setDescription(formation.getDescription());
        formationResponseDTO.setDuree(formation.getDuree());
        formationResponseDTO.setPrix(formation.getPrix());
        formationResponseDTO.setPrerequis(formation.getPrerequis());
        formationResponseDTO.setInscriptions(inscriptionListToInscriptionInFormationList(formation.getInscriptions()));
        return formationResponseDTO;

    }
    public FormationResponseDTO.FiliereDTO filiereToFiliereDTO(Filiere filiere){
        return modelMapper.map(filiere, FormationResponseDTO.FiliereDTO.class);
    }

    public InscriptionInFormationDTO inscriptionToInscriptionInFormation(Inscription inscription) {
        InscriptionInFormationDTO inscriptionInFormationDTO = new InscriptionInFormationDTO();
        inscriptionInFormationDTO.setId(inscription.getId());
        inscriptionInFormationDTO.setCandidatEmail(inscription.getCandidat().getEmail());
        inscriptionInFormationDTO.setAnneeAcademique(inscription.getAnneeAcademique().getLibelle());
        inscriptionInFormationDTO.setDateSoumission(inscription.getDateSoumission());
        return inscriptionInFormationDTO;
    }

    public List<InscriptionInFormationDTO> inscriptionListToInscriptionInFormationList(List<Inscription> inscriptions) {
        return inscriptions.stream()
                .map(this::inscriptionToInscriptionInFormation)
                .toList();
    }


    public List<FormationResponseDTO> formationListToFormationResponseDTOList(List<Formation> formations) {

        List<FormationResponseDTO> formationResponseDTOList = new ArrayList<>();
        for (Formation formation : formations) {
            formationResponseDTOList.add(formationToFormationResponseDTO(formation));
        }
        return formationResponseDTOList;


    }

}
