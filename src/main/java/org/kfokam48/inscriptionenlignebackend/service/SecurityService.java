package org.kfokam48.inscriptionenlignebackend.service;

import jakarta.servlet.http.HttpServletRequest;

public interface SecurityService {
    void loggerAction(String action, String entite, Long entiteId, String utilisateur, HttpServletRequest request);
    void loggerTentativeConnexion(String email, String ip, boolean succes, String erreur);
    boolean isCompteBloque(String email);
    boolean isIpBloquee(String ip);
    void bloquerCompte(String email, int dureeMinutes);
    String chiffrerDonneesSensibles(String donnees);
    String dechiffrerDonneesSensibles(String donneesChiffrees);
}