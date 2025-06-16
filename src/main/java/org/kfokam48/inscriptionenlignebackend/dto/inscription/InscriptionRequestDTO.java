package org.kfokam48.inscriptionenlignebackend.dto.inscription;

import lombok.Data;

@Data
public class InscriptionRequestDTO {
    private Long candidatId;
    private Long formationId;
    private Long anneeAcademiqueId;

}
