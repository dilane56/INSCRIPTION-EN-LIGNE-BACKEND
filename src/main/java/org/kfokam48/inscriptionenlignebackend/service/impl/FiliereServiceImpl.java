package org.kfokam48.inscriptionenlignebackend.service.impl;

import org.kfokam48.inscriptionenlignebackend.dto.filiere.FiliereDTO;
import org.kfokam48.inscriptionenlignebackend.dto.filiere.FiliereRequestDTO;
import org.kfokam48.inscriptionenlignebackend.mapper.FiliereMapper;
import org.kfokam48.inscriptionenlignebackend.model.Filiere;
import org.kfokam48.inscriptionenlignebackend.repository.FiliereRepository;
import org.kfokam48.inscriptionenlignebackend.service.FiliereService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FiliereServiceImpl implements FiliereService {
    
    private final FiliereRepository filiereRepository;
    private final FiliereMapper filiereMapper;
    
    public FiliereServiceImpl(FiliereRepository filiereRepository,
                             FiliereMapper filiereMapper) {
        this.filiereRepository = filiereRepository;
        this.filiereMapper = filiereMapper;
    }
    
    @Override
    public List<FiliereDTO> getAll() {
        List<Filiere> filieres = filiereRepository.findAllByOrderByNomFiliereAsc();
        return filiereMapper.toDTOList(filieres);
    }
    
    @Override
    public FiliereDTO create(FiliereRequestDTO request) {
        Filiere filiere = new Filiere();
        filiere.setNomFiliere(request.getNomFiliere());
        
        filiere = filiereRepository.save(filiere);
        return filiereMapper.toDTO(filiere);
    }
    
    @Override
    public FiliereDTO update(Long id, FiliereRequestDTO request) {
        Filiere filiere = filiereRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Filière non trouvée"));
        
        filiere.setNomFiliere(request.getNomFiliere());
        
        filiere = filiereRepository.save(filiere);
        return filiereMapper.toDTO(filiere);
    }
    
    @Override
    public void delete(Long id) {
        filiereRepository.deleteById(id);
    }
}