package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;
@Data
@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "conversation_users",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_id")
    )
    private List<User> participants;

    private Instant createdAt;



}

