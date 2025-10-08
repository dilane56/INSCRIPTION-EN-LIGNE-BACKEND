package org.kfokam48.inscriptionenlignebackend.dto.candidat;

import lombok.Data;



@Data
public class CandidatRequestDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String password;

}
