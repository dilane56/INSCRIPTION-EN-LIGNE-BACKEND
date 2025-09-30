package org.kfokam48.inscriptionenlignebackend.service.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kfokam48.inscriptionenlignebackend.model.auth.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt.expiration.milliseconds}")
    private long jwtExpirationMs;
    
    @Value("${app.oauth2.authorized-redirect-uris}")
    private String redirectUri;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException {
        
        Object principal = authentication.getPrincipal();
        String email;
        
        if (principal instanceof CustomUserDetails) {
            email = ((CustomUserDetails) principal).getUsername();
        } else if (principal instanceof org.springframework.security.oauth2.core.user.OAuth2User) {
            // Extraire l'email depuis OAuth2User
            org.springframework.security.oauth2.core.user.OAuth2User oauth2User = 
                (org.springframework.security.oauth2.core.user.OAuth2User) principal;
            email = oauth2User.getAttribute("email");
        } else {
            // Fallback
            email = authentication.getName();
        }
        
        // Générer JWT token avec l'email
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        String token = Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();
        
        // Rediriger vers le frontend avec le token
        String targetUrl = redirectUri + "?token=" + token + "&email=" + email;
        
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}