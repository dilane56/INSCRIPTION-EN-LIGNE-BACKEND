package org.kfokam48.inscriptionenlignebackend.mapper;

import org.kfokam48.inscriptionenlignebackend.dto.filiere.FiliereRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.filiere.FiliereResponseDTO;
import org.kfokam48.inscriptionenlignebackend.model.Filiere;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class FiliereMapper {
    private final ModelMapper modelMapper ;

    public FiliereMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Filiere anneeAcademiqueRequestDTOToAnneeAcademique(FiliereRequestDTO filiereRequestDTO){
        return modelMapper.map(filiereRequestDTO, Filiere.class);
    }

    public FiliereResponseDTO anneeAcademiqueToAnneeAcademiqueResponseDTO(Filiere anneeAcademique){
        return modelMapper.map(anneeAcademique, FiliereResponseDTO.class);
    }


    public List<FiliereResponseDTO> anneeAcademiqueListToAnneeAcademiqueResponseDTOList(List<Filiere> anneeAcademiques){

        List<FiliereResponseDTO> filiereResponseDTOList = new ArrayList<>();
        for (Filiere anneeAcademique : anneeAcademiques) {
            filiereResponseDTOList.add(anneeAcademiqueToAnneeAcademiqueResponseDTO(anneeAcademique));
            }
        return filiereResponseDTOList;
    }
}




