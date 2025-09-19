package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.kfokam48.inscriptionenlignebackend.model.AuditLog;
import org.kfokam48.inscriptionenlignebackend.model.TentativeConnexion;
import org.kfokam48.inscriptionenlignebackend.repository.AuditLogRepository;
import org.kfokam48.inscriptionenlignebackend.repository.TentativeConnexionRepository;
import org.kfokam48.inscriptionenlignebackend.service.SecurityService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Service("securityServiceFallback")
@ConditionalOnMissingBean(RedisTemplate.class)
public class SecurityServiceFallback implements SecurityService {
    
    private final AuditLogRepository auditLogRepository;
    private final TentativeConnexionRepository tentativeRepository;
    private final ConcurrentHashMap<String, LocalDateTime> blockedAccounts = new ConcurrentHashMap<>();
    
    public SecurityServiceFallback(AuditLogRepository auditLogRepository, 
                                  TentativeConnexionRepository tentativeRepository) {
        this.auditLogRepository = auditLogRepository;
        this.tentativeRepository = tentativeRepository;
    }

    @Override
    public void loggerAction(String action, String entite, Long entiteId, String utilisateur, HttpServletRequest request) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setEntite(entite);
        log.setEntiteId(entiteId);
        log.setUtilisateur(utilisateur);
        log.setAdresseIp(getClientIp(request));
        log.setUserAgent(request.getHeader("User-Agent"));
        auditLogRepository.save(log);
    }

    @Override
    public void loggerTentativeConnexion(String email, String ip, boolean succes, String erreur) {
        TentativeConnexion tentative = new TentativeConnexion();
        tentative.setEmail(email);
        tentative.setAdresseIp(ip);
        tentative.setSucces(succes);
        tentative.setMessageErreur(erreur);
        tentativeRepository.save(tentative);
        
        if (!succes) {
            LocalDateTime depuis = LocalDateTime.now().minusMinutes(15);
            long tentativesEchouees = tentativeRepository.countByEmailAndSuccesFalseAndDateTentativeAfter(email, depuis);
            if (tentativesEchouees >= 5) {
                bloquerCompte(email, 30);
            }
        }
    }

    @Override
    public boolean isCompteBloque(String email) {
        LocalDateTime blockedUntil = blockedAccounts.get(email);
        if (blockedUntil != null && LocalDateTime.now().isBefore(blockedUntil)) {
            return true;
        }
        blockedAccounts.remove(email);
        return false;
    }

    @Override
    public boolean isIpBloquee(String ip) {
        return false; // Implémentation simplifiée
    }

    @Override
    public void bloquerCompte(String email, int dureeMinutes) {
        blockedAccounts.put(email, LocalDateTime.now().plusMinutes(dureeMinutes));
    }

    @Override
    public String chiffrerDonneesSensibles(String donnees) {
        return donnees; // Pas de chiffrement sans Redis
    }

    @Override
    public String dechiffrerDonneesSensibles(String donneesChiffrees) {
        return donneesChiffrees; // Pas de déchiffrement sans Redis
    }
    
    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}