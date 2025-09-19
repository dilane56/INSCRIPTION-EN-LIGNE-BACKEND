package org.kfokam48.inscriptionenlignebackend.mapper;

import org.kfokam48.inscriptionenlignebackend.dto.niveau.NiveauInFromationDTO;
import org.kfokam48.inscriptionenlignebackend.dto.niveau.NiveauRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.niveau.NiveauResponseDTO;
import org.kfokam48.inscriptionenlignebackend.model.Niveau;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NiveauMapper {
    private final ModelMapper modelMapper ;

    public NiveauMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

     public Niveau niveauRequestDTOToNiveau(NiveauRequestDTO dto){
         return modelMapper.map(dto,Niveau.class);
     }
    public NiveauResponseDTO niveauToNiveauResponseDTO(Niveau niveau){
        NiveauResponseDTO niveauResponseDTO = new NiveauResponseDTO();
        niveauResponseDTO.setId(niveau.getId());
        niveauResponseDTO.setLibelle(niveau.getLibelle());
        return niveauResponseDTO;
    }
    public List<NiveauResponseDTO> niveauListToNiveauResponseDTOList(List<Niveau> niveaux){
        return niveaux.stream()
                .map(this::niveauToNiveauResponseDTO)
                .toList();
    }

    public NiveauInFromationDTO niveauToNiveauInFormationDTO(Niveau niveau){
        NiveauInFromationDTO niveauInFromationDTO = new NiveauInFromationDTO();
        niveauInFromationDTO.setId(niveau.getId());
        niveauInFromationDTO.setLibelle(niveau.getLibelle());
        return niveauInFromationDTO;
    }
}

