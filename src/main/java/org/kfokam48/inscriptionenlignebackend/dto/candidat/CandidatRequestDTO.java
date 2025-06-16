package org.kfokam48.inscriptionenlignebackend.dto.candidat;

import lombok.Data;
import org.kfokam48.inscriptionenlignebackend.dto.user.UserRequestDTO;

import java.util.Locale;


@Data
public class CandidatRequestDTO extends UserRequestDTO {
    private Locale dateNaissance;
}
