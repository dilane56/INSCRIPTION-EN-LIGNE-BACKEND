package org.kfokam48.inscriptionenlignebackend.dto.inscription;

import lombok.Data;

@Data
public class DocumentInInscription {
    private Long id;
    private String typeDocument;
    private String fichierUrl;
    private Boolean formatValide;
    private Boolean valideParOCR;
    private String commentaire;

}
