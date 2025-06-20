package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.transaction.Transactional;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatResponseDTO;
import org.kfokam48.inscriptionenlignebackend.enums.Roles;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceNotFoundException;
import org.kfokam48.inscriptionenlignebackend.mapper.CandidatMapper;
import org.kfokam48.inscriptionenlignebackend.model.Candidat;
import org.kfokam48.inscriptionenlignebackend.repository.CandidatRepository;
import org.kfokam48.inscriptionenlignebackend.service.CandidatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CandidatServiceImpl implements CandidatService {
    private final CandidatRepository candidatRepository;
    private final CandidatMapper candidatMapper;

    public CandidatServiceImpl(CandidatRepository candidatRepository, CandidatMapper candidatMapper) {
        this.candidatRepository = candidatRepository;
        this.candidatMapper = candidatMapper;
    }

    @Override
    public CandidatResponseDTO getCandidatById(Long id) {
        Candidat candidat = candidatRepository.findById(id).orElseThrow(()-> new RessourceNotFoundException("Candidat not found"));
        return candidatMapper.candidatToCandidatResponseDTO(candidat);
    }

    @Override
    public CandidatResponseDTO getCandidatByEmail(String email) {
        //Candidat candidat = candidatRepository.findByEmail(email).orElseThrow(()-> new RessourceNotFoundException("Candidat not found"));

        return null;
    }

    @Override
    public CandidatResponseDTO createCandidat(CandidatRequestDTO candidat) {
        Candidat newCandidat = candidatMapper.candidatRequestDTOToCandidat(candidat);
        newCandidat.setRole(Roles.ETUDIANT);
        return  candidatMapper.candidatToCandidatResponseDTO(candidatRepository.save(newCandidat));
    }

    @Override
    public CandidatResponseDTO updateCandidat(CandidatRequestDTO candidat, Long id) {
        Candidat newCandidat = candidatRepository.findById(id).orElseThrow(()-> new RessourceNotFoundException("Candidat not found"));
        newCandidat.setEmail(candidat.getEmail());
        newCandidat.setRole(Roles.ETUDIANT);
        newCandidat.setFirstName(candidat.getFirstName());
        newCandidat.setLastName(candidat.getLastName());
        newCandidat.setDateNaissance(candidat.getDateNaissance());
        candidatRepository.save(newCandidat);
        return candidatMapper.candidatToCandidatResponseDTO(newCandidat);
    }

    @Override
    public String deleteCandidat(Long id) {
        Candidat candidat = candidatRepository.findById(id).orElseThrow(()-> new RessourceNotFoundException("Candidat not found"));
        candidatRepository.delete(candidat);
        return "Candidat deleted Successfully";
    }

    @Override
    public List<CandidatResponseDTO> getAllCandidats() {
        return candidatMapper.candidatListToCandidatResponseDTOList(candidatRepository.findAll());
    }
}
