package org.kfokam48.inscriptionenlignebackend.dto.etape;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EtapeInscriptionDTO {
    private Long id;
    private Integer numeroEtape;
    private String nomEtape;
    private Boolean completee;
    private LocalDateTime dateCompletion;
    private Long inscriptionId;
}