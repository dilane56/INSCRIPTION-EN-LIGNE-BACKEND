package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.inscription.InscriptionRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.inscription.InscriptionResponeDTO;

import java.util.List;

public interface InscriptionService {
    public InscriptionResponeDTO findById(Long id);
    public InscriptionResponeDTO save(InscriptionRequestDTO inscriptionRequestDTO);
    public InscriptionResponeDTO update(InscriptionRequestDTO inscriptionRequestDTO, Long id);
    public String delete(Long id);
    public List<InscriptionResponeDTO> findAll();
    public List<InscriptionResponeDTO> getInscriptionsByCandidat(Long candidatId);

}
