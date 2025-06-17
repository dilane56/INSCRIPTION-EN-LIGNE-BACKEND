package org.kfokam48.inscriptionenlignebackend.mapper;

import org.kfokam48.inscriptionenlignebackend.dto.anneAcademique.AnneeAcademiqueRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.anneAcademique.AnneeAcademiqueResponseDTO;
import org.kfokam48.inscriptionenlignebackend.model.AnneeAcademique;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class AnneeAcademiqueMapper {
    private final ModelMapper modelMapper ;

    public AnneeAcademiqueMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AnneeAcademique anneeAcademiqueRequestDTOToAnneeAcademique(AnneeAcademiqueRequestDTO anneeAcademiqueRequestDTO){
        return modelMapper.map(anneeAcademiqueRequestDTO, AnneeAcademique.class);
    }

    public AnneeAcademiqueResponseDTO anneeAcademiqueToAnneeAcademiqueResponseDTO(AnneeAcademique anneeAcademique){
        return modelMapper.map(anneeAcademique, AnneeAcademiqueResponseDTO.class);
    }


    public List<AnneeAcademiqueResponseDTO> anneeAcademiqueListToAnneeAcademiqueResponseDTOList(List<AnneeAcademique> anneeAcademiques){

        List<AnneeAcademiqueResponseDTO> anneeAcademiqueResponseDTOList= new ArrayList<>();
        for (AnneeAcademique anneeAcademique : anneeAcademiques) {
            anneeAcademiqueResponseDTOList.add(anneeAcademiqueToAnneeAcademiqueResponseDTO(anneeAcademique));
            }
        return anneeAcademiqueResponseDTOList;
    }
}




