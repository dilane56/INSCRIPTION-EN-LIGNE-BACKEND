package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.transaction.Transactional;
import org.kfokam48.inscriptionenlignebackend.dto.filiere.FiliereRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.filiere.FiliereResponseDTO;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceNotFoundException;
import org.kfokam48.inscriptionenlignebackend.mapper.FiliereMapper;
import org.kfokam48.inscriptionenlignebackend.model.Filiere;
import org.kfokam48.inscriptionenlignebackend.repository.FiliereRepository;
import org.kfokam48.inscriptionenlignebackend.service.FiliereService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class FiliereServiceImpl implements FiliereService {
    private final FiliereRepository filiereRepository;
    private final FiliereMapper filiereMapper;

    public FiliereServiceImpl(FiliereRepository filiereRepository, FiliereMapper filiereMapper) {
        this.filiereRepository = filiereRepository;
        this.filiereMapper = filiereMapper;
    }

    @Override
    public List<FiliereResponseDTO> getAllAneeAcademique() {
        return filiereMapper.anneeAcademiqueListToAnneeAcademiqueResponseDTOList(filiereRepository.findAll());
    }

    @Override
    public FiliereResponseDTO getAnneeById(Long id) {
        Filiere anneeAcademique = filiereRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("AnneeAcademique not found"));
        return filiereMapper.anneeAcademiqueToAnneeAcademiqueResponseDTO(anneeAcademique);
    }

    @Override
    public FiliereResponseDTO createAnne(FiliereRequestDTO filiereRequestDTO) {
        Filiere anneeAcademique = filiereMapper.anneeAcademiqueRequestDTOToAnneeAcademique(filiereRequestDTO);
        return filiereMapper.anneeAcademiqueToAnneeAcademiqueResponseDTO(filiereRepository.save(anneeAcademique));
    }

    @Override
    public FiliereResponseDTO updateAnnee(FiliereRequestDTO filiereRequestDTO, Long id) {
        Filiere anneeAcademique = filiereRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("AnneeAcademique not found"));
        anneeAcademique.setLibelle(filiereRequestDTO.getLibelle());
        return filiereMapper.anneeAcademiqueToAnneeAcademiqueResponseDTO(filiereRepository.save(anneeAcademique));
    }

    @Override
    public String deleteAnnee(Long id) {
        Filiere anneeAcademique = filiereRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("AnneeAcademique not found"));
        filiereRepository.delete(anneeAcademique);
        return "AnneeAcademique deleted successfully";
    }
}
