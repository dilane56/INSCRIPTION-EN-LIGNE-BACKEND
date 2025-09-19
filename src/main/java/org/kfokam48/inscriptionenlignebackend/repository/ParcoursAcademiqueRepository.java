package org.kfokam48.inscriptionenlignebackend.repository;

import org.kfokam48.inscriptionenlignebackend.model.ParcoursAcademique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcoursAcademiqueRepository extends JpaRepository<ParcoursAcademique, Long> {
    List<ParcoursAcademique> findByCandidatIdOrderByDateDebutDesc(Long candidatId);
}