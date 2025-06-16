package org.kfokam48.inscriptionenlignebackend.dto.candidat;

import lombok.Data;
import org.kfokam48.inscriptionenlignebackend.dto.user.UserResponseDTO;

import java.time.LocalDate;

@Data
public class CandidatResponseDTO extends UserResponseDTO {
    private LocalDate dateNaissance;
}
