package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.transaction.Transactional;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatResponseDTO;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.CoordonneesDTO;
import org.kfokam48.inscriptionenlignebackend.enums.Roles;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceAlreadyExistException;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceNotFoundException;
import org.kfokam48.inscriptionenlignebackend.mapper.CandidatMapper;
import org.kfokam48.inscriptionenlignebackend.model.Candidat;
import org.kfokam48.inscriptionenlignebackend.repository.CandidatRepository;
import org.kfokam48.inscriptionenlignebackend.repository.UserRepository;
import org.kfokam48.inscriptionenlignebackend.service.CandidatService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CandidatServiceImpl implements CandidatService {
    private final CandidatRepository candidatRepository;
    private final CandidatMapper candidatMapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public CandidatServiceImpl(CandidatRepository candidatRepository, CandidatMapper candidatMapper, UserRepository userRepository) {
        this.candidatRepository = candidatRepository;
        this.candidatMapper = candidatMapper;
        this.userRepository = userRepository;
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
        if(userRepository.existsByEmail(candidat.getEmail())){
            throw new RessourceAlreadyExistException("Email already exists");
        }else{
            Candidat newCandidat = candidatMapper.candidatRequestDTOToCandidat(candidat);
            newCandidat.setRole(Roles.CANDIDAT);
            newCandidat.setPassword(passwordEncoder.encode(candidat.getPassword()));
            return  candidatMapper.candidatToCandidatResponseDTO(candidatRepository.save(newCandidat));
        }

    }

    @Override
    public CandidatResponseDTO updateCandidat(CandidatRequestDTO candidat, Long id) {
        Candidat newCandidat = candidatRepository.findById(id).orElseThrow(()-> new RessourceNotFoundException("Candidat not found"));
        if(userRepository.existsByEmail(candidat.getEmail())&& newCandidat.getEmail().equals(candidat.getEmail())){

                newCandidat.setEmail(candidat.getEmail());
                newCandidat.setRole(Roles.CANDIDAT);
                newCandidat.setFirstName(candidat.getFirstName());
                newCandidat.setLastName(candidat.getLastName());
                newCandidat.setDateNaissance(candidat.getDateNaissance());
                newCandidat.setPassword(passwordEncoder.encode(candidat.getPassword()));

                candidatRepository.save(newCandidat);
                return candidatMapper.candidatToCandidatResponseDTO(newCandidat);
            }else{
                throw new RessourceAlreadyExistException("Email already exists");
            }



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
    
    @Override
    public void updateCoordonnees(Long candidatId, CoordonneesDTO coordonneesData) {
        Candidat candidat = candidatRepository.findById(candidatId)
            .orElseThrow(() -> new RessourceNotFoundException("Candidat not found"));
        
        // Mettre à jour uniquement les coordonnées
        if (coordonneesData.getAdresse() != null) {
            candidat.setAdresse(coordonneesData.getAdresse());
        }
        if (coordonneesData.getVille() != null) {
            candidat.setVille(coordonneesData.getVille());
        }
        if (coordonneesData.getCodePostal() != null) {
            candidat.setCodePostal(coordonneesData.getCodePostal());
        }
        if (coordonneesData.getPays() != null) {
            candidat.setPays(coordonneesData.getPays());
        }
        if (coordonneesData.getContactPourUrgence() != null) {
            candidat.setContactPourUrgence(coordonneesData.getContactPourUrgence());
        }
        if (coordonneesData.getTelephoneUrgence() != null) {
            candidat.setTelephoneUrgence(coordonneesData.getTelephoneUrgence());
        }
        
        candidatRepository.save(candidat);
    }
}
