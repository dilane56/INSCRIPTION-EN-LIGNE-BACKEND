package org.kfokam48.inscriptionenlignebackend.mapper;

import org.kfokam48.inscriptionenlignebackend.dto.etape.EtapeInscriptionDTO;
import org.kfokam48.inscriptionenlignebackend.model.EtapeInscription;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EtapeInscriptionMapper {
    
    private final ModelMapper modelMapper;
    
    public EtapeInscriptionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public EtapeInscriptionDTO toDTO(EtapeInscription etape) {
        EtapeInscriptionDTO dto = modelMapper.map(etape, EtapeInscriptionDTO.class);
        dto.setInscriptionId(etape.getInscription().getId());
        return dto;
    }
    
    public List<EtapeInscriptionDTO> toDTOList(List<EtapeInscription> etapes) {
        return etapes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}