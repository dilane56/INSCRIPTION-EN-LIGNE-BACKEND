package org.kfokam48.inscriptionenlignebackend.dto.notification;

import lombok.Data;
import org.kfokam48.inscriptionenlignebackend.enums.TypeNotification;

import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private Long id;
    private TypeNotification type;
    private String titre;
    private String contenu;
    private String statut;
    private LocalDateTime dateCreation;
    private LocalDateTime dateEnvoi;
    private LocalDateTime dateLecture;
    private Boolean lue;
    private Long inscriptionId;
    private Long candidatId;
}