package org.kfokam48.inscriptionenlignebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/oauth2")
@CrossOrigin("*")
public class OAuth2Controller {
    
    @GetMapping("/authorization/google")
    public ResponseEntity<Map<String, String>> getGoogleAuthUrl() {
        String authUrl = "/oauth2/authorization/google";
        return ResponseEntity.ok(Map.of("authUrl", authUrl));
    }
    
    @GetMapping("/authorization/microsoft")
    public ResponseEntity<Map<String, String>> getMicrosoftAuthUrl() {
        String authUrl = "/oauth2/authorization/microsoft";
        return ResponseEntity.ok(Map.of("authUrl", authUrl));
    }
}