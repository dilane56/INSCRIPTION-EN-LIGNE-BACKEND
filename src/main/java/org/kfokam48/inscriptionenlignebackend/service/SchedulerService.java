package org.kfokam48.inscriptionenlignebackend.service;

public interface SchedulerService {
    void planifierRappelInscription(Long inscriptionId, int joursDelai);
    void annulerRappelInscription(Long inscriptionId);
    void executerRappelsQuotidiens();
}