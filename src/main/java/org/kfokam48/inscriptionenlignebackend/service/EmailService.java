package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.model.Inscription;

public interface EmailService {
    void envoyerEmailSoumission(Inscription inscription);
    void envoyerEmailValidation(Inscription inscription);
    void envoyerEmailRejet(Inscription inscription);
    void envoyerEmailRappel(Inscription inscription);
    void envoyerEmailBienvenue(String email, String nom);
}