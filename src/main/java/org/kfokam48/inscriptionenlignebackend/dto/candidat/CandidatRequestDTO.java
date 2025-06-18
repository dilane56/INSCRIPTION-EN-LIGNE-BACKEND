package org.kfokam48.inscriptionenlignebackend.dto.candidat;

import lombok.Data;
import org.kfokam48.inscriptionenlignebackend.dto.user.UserRequestDTO;

import java.time.LocalDate;



@Data
public class CandidatRequestDTO extends UserRequestDTO {
    private LocalDate dateNaissance;
}
