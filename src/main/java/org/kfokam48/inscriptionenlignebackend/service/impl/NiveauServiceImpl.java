package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.transaction.Transactional;
import org.kfokam48.inscriptionenlignebackend.dto.niveau.NiveauRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.niveau.NiveauResponseDTO;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceNotFoundException;
import org.kfokam48.inscriptionenlignebackend.mapper.NiveauMapper;
import org.kfokam48.inscriptionenlignebackend.model.Niveau;
import org.kfokam48.inscriptionenlignebackend.repository.NiveauRepository;
import org.kfokam48.inscriptionenlignebackend.service.NiveauService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class NiveauServiceImpl implements NiveauService {

    private final NiveauRepository niveauRepository;
    private final NiveauMapper niveauMapper;

    public NiveauServiceImpl(NiveauRepository niveauRepository, NiveauMapper niveauMapper) {
        this.niveauRepository = niveauRepository;
        this.niveauMapper = niveauMapper;
    }

    @Override
    public NiveauResponseDTO createNiveau(NiveauRequestDTO dto) {
        Niveau niveau = niveauMapper.niveauRequestDTOToNiveau(dto);
        return niveauMapper.niveauToNiveauResponseDTO(niveauRepository.save(niveau));
    }

    @Override
    public NiveauResponseDTO updateNiveau(Long id, NiveauRequestDTO dto) {
        Niveau niveau = niveauRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Niveau not found"));
        niveau.setLibelle(dto.getLibelle());
        return niveauMapper.niveauToNiveauResponseDTO(niveauRepository.save(niveau));
    }

    @Override
    public List<NiveauResponseDTO> getAllNiveaux() {
        return niveauMapper.niveauListToNiveauResponseDTOList(niveauRepository.findAll());
    }

    @Override
    public String deleteNiveau(Long id) {
        Niveau niveau = niveauRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Niveau not found"));
        niveauRepository.delete(niveau);
        return "Niveau deleted successfully";
    }
}

