package org.kfokam48.inscriptionenlignebackend.dto.document;

import lombok.Data;

@Data
public class DocumentRequestDTO {
    private String typeDocument;
    private Long inscriptionId;

}
