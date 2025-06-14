package org.kfokam48.inscriptionenlignebackend.repository;

import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncriptionRepository extends JpaRepository<Inscription,Long> {
}
