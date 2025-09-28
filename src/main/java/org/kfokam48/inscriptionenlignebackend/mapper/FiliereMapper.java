package org.kfokam48.inscriptionenlignebackend.mapper;

import org.kfokam48.inscriptionenlignebackend.dto.filiere.FiliereDTO;
import org.kfokam48.inscriptionenlignebackend.model.Filiere;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class FiliereMapper {
    private  final ModelMapper modelMapper;

    public FiliereMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FiliereDTO toDTO(Filiere filiere){
        return modelMapper.map(filiere, FiliereDTO.class);
    };
    public List<FiliereDTO> toDTOList(List<Filiere> filieres){
        return filieres.stream().map(this::toDTO).toList();
    };
}