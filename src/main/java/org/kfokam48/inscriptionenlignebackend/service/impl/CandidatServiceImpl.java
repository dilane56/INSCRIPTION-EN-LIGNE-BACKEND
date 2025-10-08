package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.transaction.Transactional;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatResponseDTO;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatProfileUpdateDTO;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatBasicInfoDTO;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.CoordonneesDTO;
import org.kfokam48.inscriptionenlignebackend.enums.Roles;
import org.kfokam48.inscriptionenlignebackend.enums.Sexe;
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
        if(!userRepository.existsByEmail(candidat.getEmail()) || newCandidat.getEmail().equals(candidat.getEmail())){

                newCandidat.setEmail(candidat.getEmail());
                newCandidat.setRole(Roles.CANDIDAT);
                newCandidat.setFirstName(candidat.getFirstName());
                newCandidat.setLastName(candidat.getLastName());
                if(candidat.getPassword() != null && !candidat.getPassword().isEmpty()) {
                    newCandidat.setPassword(passwordEncoder.encode(candidat.getPassword()));
                }

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
    
    public void updateProfile(Long candidatId, CandidatProfileUpdateDTO profileData) {
        Candidat candidat = candidatRepository.findById(candidatId)
            .orElseThrow(() -> new RessourceNotFoundException("Candidat not found"));
        
        // Mettre à jour les informations User
        if (profileData.getFirstName() != null) {
            candidat.setFirstName(profileData.getFirstName());
        }
        if (profileData.getLastName() != null) {
            candidat.setLastName(profileData.getLastName());
        }
        
        // Mettre à jour les informations Candidat
        if (profileData.getPhone() != null) {
            candidat.setPhone(profileData.getPhone());
        }
        if (profileData.getDateNaissance() != null) {
            candidat.setDateNaissance(profileData.getDateNaissance());
        }
        if (profileData.getNationalite() != null) {
            candidat.setNationalite(profileData.getNationalite());
        }
        if (profileData.getTypeDePieceIdentite() != null) {
            candidat.setTypeDePieceIdentite(profileData.getTypeDePieceIdentite());
        }
        if (profileData.getNumeroPieceIdentite() != null) {
            candidat.setNumeroPieceIdentite(profileData.getNumeroPieceIdentite());
        }
        if (profileData.getAdresse() != null) {
            candidat.setAdresse(profileData.getAdresse());
        }
        if (profileData.getVille() != null) {
            candidat.setVille(profileData.getVille());
        }
        if (profileData.getCodePostal() != null) {
            candidat.setCodePostal(profileData.getCodePostal());
        }
        if (profileData.getPays() != null) {
            candidat.setPays(profileData.getPays());
        }
        if (profileData.getContactPourUrgence() != null) {
            candidat.setContactPourUrgence(profileData.getContactPourUrgence());
        }
        if (profileData.getTelephoneUrgence() != null) {
            candidat.setTelephoneUrgence(profileData.getTelephoneUrgence());
        }
        if (profileData.getSexe() != null) {
            candidat.setSexe(profileData.getSexe());
        }
        
        candidatRepository.save(candidat);
    }
    
    public void updateBasicInfo(Long candidatId, CandidatBasicInfoDTO basicInfo) {
        Candidat candidat = candidatRepository.findById(candidatId)
            .orElseThrow(() -> new RessourceNotFoundException("Candidat not found"));
        
        // Mettre à jour les informations de base
        if (basicInfo.getFirstName() != null) {
            candidat.setFirstName(basicInfo.getFirstName());
        }
        if (basicInfo.getLastName() != null) {
            candidat.setLastName(basicInfo.getLastName());
        }
        if (basicInfo.getDateNaissance() != null) {
            candidat.setDateNaissance(basicInfo.getDateNaissance());
        }
        if (basicInfo.getNationalite() != null) {
            candidat.setNationalite(basicInfo.getNationalite());
        }
        if (basicInfo.getSexe() != null) {
            try {
                candidat.setSexe(Sexe.valueOf(basicInfo.getSexe()));
            } catch (IllegalArgumentException e) {
                // Gérer les valeurs non-binaires ou autres
                if ("Non-binaire".equals(basicInfo.getSexe())) {
                    candidat.setSexe(Sexe.NON_BINAIRE);
                }
            }
        }
        
        candidatRepository.save(candidat);
    }
}
