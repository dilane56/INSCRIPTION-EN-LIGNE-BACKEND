package org.kfokam48.inscriptionenlignebackend.repository;

import org.kfokam48.inscriptionenlignebackend.enums.TypeNotification;
import org.kfokam48.inscriptionenlignebackend.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByCandidatIdOrderByDateCreationDesc(Long candidatId);
    List<Notification> findByInscriptionIdAndTypeAndStatut(Long inscriptionId, TypeNotification type, String statut);
    List<Notification> findByCandidatIdAndLueFalse(Long candidatId);
    long countByCandidatIdAndLueFalse(Long candidatId);
}
