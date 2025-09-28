package org.kfokam48.inscriptionenlignebackend.repository;

import org.kfokam48.inscriptionenlignebackend.model.Etablissement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, Long> {
    List<Etablissement> findByActifTrueOrderByNomAsc();
    List<Etablissement> findAllByOrderByNomAsc();
}