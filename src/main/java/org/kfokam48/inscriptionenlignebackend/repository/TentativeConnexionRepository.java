package org.kfokam48.inscriptionenlignebackend.repository;

import org.kfokam48.inscriptionenlignebackend.model.TentativeConnexion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TentativeConnexionRepository extends JpaRepository<TentativeConnexion, Long> {
    List<TentativeConnexion> findByEmailAndSuccesFalseAndDateTentativeAfter(String email, LocalDateTime depuis);
    List<TentativeConnexion> findByAdresseIpAndSuccesFalseAndDateTentativeAfter(String ip, LocalDateTime depuis);
    long countByEmailAndSuccesFalseAndDateTentativeAfter(String email, LocalDateTime depuis);
}