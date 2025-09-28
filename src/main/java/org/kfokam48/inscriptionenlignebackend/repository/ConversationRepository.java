package org.kfokam48.inscriptionenlignebackend.repository;


import org.kfokam48.inscriptionenlignebackend.model.Conversation;
import org.kfokam48.inscriptionenlignebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("""
        SELECT c FROM Conversation c
        WHERE :user1 MEMBER OF c.participants
        AND :user2 MEMBER OF c.participants
    """)
    Optional<Conversation> findByParticipants(@Param("user1") User user1,
                                              @Param("user2") User user2);

    // Nouvelle méthode pour récupérer toutes les conversations d'un utilisateur
    @Query("SELECT c FROM Conversation c WHERE :user MEMBER OF c.participants")
    List<Conversation> findByParticipantsContaining(@Param("user") User user);

}
