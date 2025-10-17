package org.kfokam48.inscriptionenlignebackend.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.kfokam48.inscriptionenlignebackend.dto.auth.LoginRequest;
import org.kfokam48.inscriptionenlignebackend.dto.auth.LoginResponse;
import org.kfokam48.inscriptionenlignebackend.dto.auth.EmailVerificationDTO;
import org.kfokam48.inscriptionenlignebackend.dto.auth.VerifyCodeDTO;
import org.kfokam48.inscriptionenlignebackend.model.User;
import org.kfokam48.inscriptionenlignebackend.service.auth.AuthService;
import org.kfokam48.inscriptionenlignebackend.service.EmailService;
import org.springframework.security.core.Authentication;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;
    private final ConcurrentHashMap<String, String> verificationCodes = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, LocalDateTime> codeExpiry = new ConcurrentHashMap<>();

    public AuthController(AuthService authService, EmailService emailService) {
        this.authService = authService;
        this.emailService = emailService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest request) {
        String clientIp = getClientIp(request);
        LoginResponse response = authService.authenticateUser(loginRequest, clientIp);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/oauth2/google")
    public ResponseEntity<Map<String, String>> getGoogleAuthUrl() {
        return ResponseEntity.ok(Map.of("authUrl", "/oauth2/authorization/google"));
    }
    
    @GetMapping("/oauth2/microsoft")
    public ResponseEntity<Map<String, String>> getMicrosoftAuthUrl() {
        return ResponseEntity.ok(Map.of("authUrl", "/oauth2/authorization/microsoft"));
    }
    
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        
        if (token == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Token manquant"));
        }
        
        try {
            String email = authService.getEmailFromToken(token);
            
            User user;
            try {
                user = authService.getUserByEmail(email);
            } catch (Exception e) {
                // Créer automatiquement l'utilisateur OAuth2 s'il n'existe pas
                user = authService.createOAuth2User(email);
            }
            
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("firstName", user.getFirstName());
            userInfo.put("lastName", user.getLastName());
            userInfo.put("email", user.getEmail());
            userInfo.put("role", user.getRole());
            
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Token invalide"));
        }
    }
    
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    
    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
    
    @PostMapping("/send-verification")
    public ResponseEntity<Map<String, String>> sendEmailVerification(
            @RequestBody EmailVerificationDTO emailDto,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = authService.getUserByEmail(email);
            
            // Vérifier si l'email est déjà vérifié
            if (user.getEmailVerified()) {
                return ResponseEntity.ok(Map.of("message", "Email déjà vérifié"));
            }
            
            // Générer un code à 6 chiffres
            String code = String.format("%06d", new Random().nextInt(1000000));
            
            // Stocker le code avec expiration (10 minutes)
            verificationCodes.put(user.getEmail(), code);
            codeExpiry.put(user.getEmail(), LocalDateTime.now().plusMinutes(10));
            
            // Envoyer l'email de vérification
            emailService.sendEmailVerification(user.getEmail(), code);
            
            return ResponseEntity.ok(Map.of("message", "Email de vérification envoyé"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Impossible d'envoyer l'email de vérification"));
        }
    }
    
    @PostMapping("/verify-code")
    public ResponseEntity<Map<String, String>> verifyCode(
            @RequestBody VerifyCodeDTO codeDto,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            String providedCode = codeDto.getCode();
            
            // Vérifier si le code existe et n'est pas expiré
            String storedCode = verificationCodes.get(email);
            LocalDateTime expiry = codeExpiry.get(email);
            
            if (storedCode == null || expiry == null) {
                return ResponseEntity.status(400).body(Map.of("error", "Aucun code de vérification trouvé"));
            }
            
            if (LocalDateTime.now().isAfter(expiry)) {
                verificationCodes.remove(email);
                codeExpiry.remove(email);
                return ResponseEntity.status(400).body(Map.of("error", "Code expiré"));
            }
            
            if (!storedCode.equals(providedCode)) {
                return ResponseEntity.status(400).body(Map.of("error", "Code incorrect"));
            }
            
            // Code valide - marquer l'email comme vérifié
            User user = authService.getUserByEmail(email);
            user.setEmailVerified(true);
            authService.saveUser(user);
            
            // Nettoyer les codes
            verificationCodes.remove(email);
            codeExpiry.remove(email);
            
            return ResponseEntity.ok(Map.of("message", "Email vérifié avec succès"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Erreur lors de la vérification"));
        }
    }
}