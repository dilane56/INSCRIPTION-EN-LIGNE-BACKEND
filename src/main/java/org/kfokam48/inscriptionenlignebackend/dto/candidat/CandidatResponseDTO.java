package org.kfokam48.inscriptionenlignebackend.dto.candidat;

import lombok.Data;
import org.kfokam48.inscriptionenlignebackend.dto.user.UserResponseDTO;

import java.time.LocalDate;
import java.util.List;

@Data
public class CandidatResponseDTO extends UserResponseDTO {
    private LocalDate dateNaissance;
    private List<InscriptionInCandidatDTO> inscriptions;
}
