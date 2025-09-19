package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.etape.EtapeInscriptionDTO;

import java.util.List;

public interface EtapeInscriptionService {
    List<EtapeInscriptionDTO> getEtapesByInscription(Long inscriptionId);
    EtapeInscriptionDTO completerEtape(Long inscriptionId, Integer numeroEtape);
    Double calculerPourcentageCompletion(Long inscriptionId);
}