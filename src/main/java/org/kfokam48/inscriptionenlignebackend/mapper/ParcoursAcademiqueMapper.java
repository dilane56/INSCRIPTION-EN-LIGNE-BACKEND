package org.kfokam48.inscriptionenlignebackend.mapper;

import org.kfokam48.inscriptionenlignebackend.dto.parcours.ParcoursAcademiqueDTO;
import org.kfokam48.inscriptionenlignebackend.model.ParcoursAcademique;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParcoursAcademiqueMapper {
    
    private final ModelMapper modelMapper;
    
    public ParcoursAcademiqueMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public ParcoursAcademiqueDTO toDTO(ParcoursAcademique parcours) {
        ParcoursAcademiqueDTO dto = modelMapper.map(parcours, ParcoursAcademiqueDTO.class);
        dto.setCandidatId(parcours.getCandidat().getId());
        return dto;
    }
    
    public ParcoursAcademique toEntity(ParcoursAcademiqueDTO dto) {
        return modelMapper.map(dto, ParcoursAcademique.class);
    }
    
    public List<ParcoursAcademiqueDTO> toDTOList(List<ParcoursAcademique> parcours) {
        return parcours.stream().map(this::toDTO).collect(Collectors.toList());
    }
}