package org.kfokam48.inscriptionenlignebackend.repository;

import org.kfokam48.inscriptionenlignebackend.enums.StatutInscription;
import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    List<Inscription> findByCandidatId(Long candidatId);
    List<Inscription> findByStatut(StatutInscription statut);
    List<Inscription> findByStatutAndDerniereModificationBefore(StatutInscription statut, LocalDateTime date);
    
    @Query("SELECT COUNT(i) FROM Inscription i WHERE i.statut = ?1")
    long countByStatut(StatutInscription statut);
    
    @Query("SELECT i FROM Inscription i WHERE i.statut = org.kfokam48.inscriptionenlignebackend.enums.StatutInscription.SOUMISE ORDER BY i.dateSoumission ASC")
    List<Inscription> findInscriptionsEnAttenteValidation();
}
