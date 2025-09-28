package org.kfokam48.inscriptionenlignebackend.service.impl;

import org.kfokam48.inscriptionenlignebackend.dto.etablissement.EtablissementDTO;
import org.kfokam48.inscriptionenlignebackend.dto.etablissement.EtablissementRequestDTO;
import org.kfokam48.inscriptionenlignebackend.mapper.EtablissementMapper;
import org.kfokam48.inscriptionenlignebackend.model.Etablissement;
import org.kfokam48.inscriptionenlignebackend.repository.EtablissementRepository;
import org.kfokam48.inscriptionenlignebackend.service.EtablissementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtablissementServiceImpl implements EtablissementService {
    
    private final EtablissementRepository etablissementRepository;
    private final EtablissementMapper etablissementMapper;
    
    public EtablissementServiceImpl(EtablissementRepository etablissementRepository,
                                   EtablissementMapper etablissementMapper) {
        this.etablissementRepository = etablissementRepository;
        this.etablissementMapper = etablissementMapper;
    }
    
    @Override
    public List<EtablissementDTO> getAllActifs() {
        List<Etablissement> etablissements = etablissementRepository.findByActifTrueOrderByNomAsc();
        return etablissementMapper.toDTOList(etablissements);
    }
    
    @Override
    public List<EtablissementDTO> getAll() {
        List<Etablissement> etablissements = etablissementRepository.findAllByOrderByNomAsc();
        return etablissementMapper.toDTOList(etablissements);
    }
    
    @Override
    public EtablissementDTO create(EtablissementRequestDTO request) {
        Etablissement etablissement = new Etablissement();
        etablissement.setNom(request.getNom());
        etablissement.setVille(request.getVille());
        etablissement.setPays(request.getPays());
        etablissement.setType(request.getType());
        etablissement.setActif(request.getActif());
        
        etablissement = etablissementRepository.save(etablissement);
        return etablissementMapper.toDTO(etablissement);
    }
    
    @Override
    public EtablissementDTO update(Long id, EtablissementRequestDTO request) {
        Etablissement etablissement = etablissementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Etablissement non trouvé"));
        
        etablissement.setNom(request.getNom());
        etablissement.setVille(request.getVille());
        etablissement.setPays(request.getPays());
        etablissement.setType(request.getType());
        etablissement.setActif(request.getActif());
        
        etablissement = etablissementRepository.save(etablissement);
        return etablissementMapper.toDTO(etablissement);
    }
    
    @Override
    public void delete(Long id) {
        etablissementRepository.deleteById(id);
    }
}