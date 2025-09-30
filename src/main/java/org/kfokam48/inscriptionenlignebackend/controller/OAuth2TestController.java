package org.kfokam48.inscriptionenlignebackend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/oauth2-test")
@CrossOrigin("*")
public class OAuth2TestController {
    
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getOAuth2User(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        
        if (authentication != null) {
            response.put("authenticated", true);
            response.put("name", authentication.getName());
            response.put("authorities", authentication.getAuthorities());
            response.put("principalType", authentication.getPrincipal().getClass().getSimpleName());
            
            if (authentication.getPrincipal() instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
                response.put("attributes", oauth2User.getAttributes());
            }
        } else {
            response.put("authenticated", false);
        }
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/success")
    public ResponseEntity<String> success() {
        return ResponseEntity.ok("OAuth2 authentication successful! You can close this window.");
    }
}