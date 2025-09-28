package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kfokam48.inscriptionenlignebackend.enums.MessageStatus;


import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User expediteur;

    @ManyToOne
    private User destinataire;

    private String contenu;

    private Instant dateEnvoi;

    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    private Boolean lu = false;

    @ManyToOne
    private Conversation conversation;
}
