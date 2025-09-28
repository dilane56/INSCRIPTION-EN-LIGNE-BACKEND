package org.kfokam48.inscriptionenlignebackend.repository;

import org.kfokam48.inscriptionenlignebackend.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByCandidatIdOrderByDateCreationDesc(Long candidatId);
    List<Notification> findByCandidatIdAndLueFalseOrderByDateCreationDesc(Long candidatId);
    long countByCandidatIdAndLueFalse(Long candidatId);
    List<Notification> findAllByOrderByDateCreationDesc();
}