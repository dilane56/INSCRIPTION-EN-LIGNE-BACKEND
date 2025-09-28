package org.kfokam48.inscriptionenlignebackend.repository;

import org.kfokam48.inscriptionenlignebackend.model.Candidat;
import org.kfokam48.inscriptionenlignebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Long> {



}
