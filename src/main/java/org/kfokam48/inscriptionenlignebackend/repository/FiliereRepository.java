package org.kfokam48.inscriptionenlignebackend.repository;

import org.kfokam48.inscriptionenlignebackend.model.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FiliereRepository extends JpaRepository<Filiere, Long> {
    List<Filiere> findAllByOrderByNomFiliereAsc();
    Optional<Filiere> findByNomFiliere(String nomFiliere);
}