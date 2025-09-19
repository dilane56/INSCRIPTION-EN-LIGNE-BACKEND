package org.kfokam48.inscriptionenlignebackend.repository;

import org.kfokam48.inscriptionenlignebackend.model.EtapeInscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtapeInscriptionRepository extends JpaRepository<EtapeInscription, Long> {
    List<EtapeInscription> findByInscriptionIdOrderByNumeroEtape(Long inscriptionId);
}