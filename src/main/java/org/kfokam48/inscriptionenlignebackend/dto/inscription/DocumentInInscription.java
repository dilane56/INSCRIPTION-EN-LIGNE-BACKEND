package org.kfokam48.inscriptionenlignebackend.dto.inscription;

import lombok.Data;

@Data
public class DocumentInInscription {
    private Long id;
    private String typeDocument;
    private String nom;
    private byte[] data;
    private Boolean valide;
    private String commentaire;

}
