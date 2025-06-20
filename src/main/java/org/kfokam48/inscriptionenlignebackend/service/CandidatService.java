package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatResponseDTO;

import java.util.List;

public interface CandidatService {
    public CandidatResponseDTO getCandidatById(Long id);
    public CandidatResponseDTO getCandidatByEmail(String email);
    public CandidatResponseDTO createCandidat(CandidatRequestDTO candidat);
    public CandidatResponseDTO updateCandidat(CandidatRequestDTO candidat, Long id);
    public String deleteCandidat(Long id);
    public List<CandidatResponseDTO> getAllCandidats();


}
