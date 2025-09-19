package org.kfokam48.inscriptionenlignebackend.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.kfokam48.inscriptionenlignebackend.dto.auth.LoginRequest;
import org.kfokam48.inscriptionenlignebackend.dto.auth.LoginResponse;
import org.kfokam48.inscriptionenlignebackend.model.User;
import org.kfokam48.inscriptionenlignebackend.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
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
            return ResponseEntity.status(401).build();
        }
        
        try {
            String email = authService.getEmailFromToken(token);
            User user = authService.getUserByEmail(email);
            
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("firstName", user.getFirstName());
            userInfo.put("lastName", user.getLastName());
            userInfo.put("email", user.getEmail());
            userInfo.put("role", user.getRole());
            
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
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
}