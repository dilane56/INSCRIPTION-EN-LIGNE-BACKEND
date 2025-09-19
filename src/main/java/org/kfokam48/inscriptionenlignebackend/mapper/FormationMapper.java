package org.kfokam48.inscriptionenlignebackend.mapper;

import org.kfokam48.inscriptionenlignebackend.dto.formation.FormationRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.formation.FormationResponseDTO;
import org.kfokam48.inscriptionenlignebackend.dto.formation.InscriptionInFormationDTO;
import org.kfokam48.inscriptionenlignebackend.model.Formation;
import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class FormationMapper {
    private final ModelMapper modelMapper;
    private final NiveauMapper niveauMapper;

    public FormationMapper(ModelMapper modelMapper, NiveauMapper niveauMapper) {
        this.modelMapper = modelMapper;
        this.niveauMapper = niveauMapper;
    }

    public Formation formationRequestDTOToFormation(FormationRequestDTO formationRequestDTO) {
        return modelMapper.map(formationRequestDTO, Formation.class);
    }

    public FormationResponseDTO formationToFormationResponseDTO(Formation formation) {
        FormationResponseDTO formationResponseDTO = new FormationResponseDTO();
        formationResponseDTO.setId(formation.getId());
        formationResponseDTO.setNomFormation(formation.getNomFormation());
        formationResponseDTO.setEtablissement(formation.getEtablissement());
        formationResponseDTO.setSpecialite(formation.getSpecialite());
        formationResponseDTO.setNiveau(niveauMapper.niveauToNiveauInFormationDTO(formation.getNiveau()));
        formationResponseDTO.setInscriptions(inscriptionListToInscriptionInFormationList(formation.getInscriptions()));
        return formationResponseDTO;

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
