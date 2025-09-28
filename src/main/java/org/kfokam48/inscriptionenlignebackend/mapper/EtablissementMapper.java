package org.kfokam48.inscriptionenlignebackend.mapper;

import org.kfokam48.inscriptionenlignebackend.dto.etablissement.EtablissementDTO;
import org.kfokam48.inscriptionenlignebackend.model.Etablissement;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class EtablissementMapper {
    private final ModelMapper modelMapper;

    public EtablissementMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EtablissementDTO toDTO(Etablissement etablissement){
        return modelMapper.map(etablissement, EtablissementDTO.class);
    }
    public List<EtablissementDTO> toDTOList(List<Etablissement> etablissements){
        return etablissements.stream().map(this::toDTO).toList();
    };
}