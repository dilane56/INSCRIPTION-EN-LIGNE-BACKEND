package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.kfokam48.inscriptionenlignebackend.model.AuditLog;
import org.kfokam48.inscriptionenlignebackend.model.TentativeConnexion;
import org.kfokam48.inscriptionenlignebackend.repository.AuditLogRepository;
import org.kfokam48.inscriptionenlignebackend.repository.TentativeConnexionRepository;
import org.kfokam48.inscriptionenlignebackend.service.SecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Service
@Primary
@ConditionalOnClass(RedisTemplate.class)
public class SecurityServiceImpl implements SecurityService {
    
    private final AuditLogRepository auditLogRepository;
    private final TentativeConnexionRepository tentativeRepository;
    private final RedisTemplate<String, String> redisTemplate;
    
    @Value("${app.security.max-tentatives:5}")
    private int maxTentatives;
    
    @Value("${app.security.duree-blocage:30}")
    private int dureeBlocage;
    
    private final SecretKey secretKey;
    
    public SecurityServiceImpl(AuditLogRepository auditLogRepository, 
                              TentativeConnexionRepository tentativeRepository,
                              RedisTemplate<String, String> redisTemplate) {
        this.auditLogRepository = auditLogRepository;
        this.tentativeRepository = tentativeRepository;
        this.redisTemplate = redisTemplate;
        this.secretKey = generateKey();
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
        
        // Bloquer si trop de tentatives échouées
        if (!succes) {
            LocalDateTime depuis = LocalDateTime.now().minusMinutes(15);
            long tentativesEchouees = tentativeRepository.countByEmailAndSuccesFalseAndDateTentativeAfter(email, depuis);
            
            if (tentativesEchouees >= maxTentatives) {
                bloquerCompte(email, dureeBlocage);
            }
        }
    }

    @Override
    public boolean isCompteBloque(String email) {
        String key = "blocked_account:" + email;
        return redisTemplate.hasKey(key);
    }

    @Override
    public boolean isIpBloquee(String ip) {
        String key = "blocked_ip:" + ip;
        return redisTemplate.hasKey(key);
    }

    @Override
    public void bloquerCompte(String email, int dureeMinutes) {
        String key = "blocked_account:" + email;
        redisTemplate.opsForValue().set(key, "blocked", dureeMinutes, TimeUnit.MINUTES);
    }

    @Override
    public String chiffrerDonneesSensibles(String donnees) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(donnees.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Erreur chiffrement", e);
        }
    }

    @Override
    public String dechiffrerDonneesSensibles(String donneesChiffrees) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(donneesChiffrees));
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("Erreur déchiffrement", e);
        }
    }
    
    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
    
    private SecretKey generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            return keyGen.generateKey();
        } catch (Exception e) {
            // Clé par défaut pour développement
            byte[] key = "MySecretKey12345".getBytes();
            return new SecretKeySpec(key, "AES");
        }
    }
}