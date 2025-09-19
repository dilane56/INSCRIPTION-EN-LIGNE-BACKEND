package org.kfokam48.inscriptionenlignebackend.service.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;

import org.kfokam48.inscriptionenlignebackend.dto.auth.LoginRequest;
import org.kfokam48.inscriptionenlignebackend.dto.auth.LoginResponse;
import org.kfokam48.inscriptionenlignebackend.enums.Roles;
import org.kfokam48.inscriptionenlignebackend.exception.AuthenticationFailedException;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceNotFoundException;
import org.kfokam48.inscriptionenlignebackend.mapper.UserMapper;
import org.kfokam48.inscriptionenlignebackend.model.User;
import org.kfokam48.inscriptionenlignebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class AuthService {


    @Value("${jwt.secret}")
    private String jwtSecretString; // La clé secrète lue depuis la configuration
    // Dans AuthService
    @Value("${jwt.expiration.milliseconds}")
    private long jwtExpirationMs;

    private SecretKey signingKey; // Pour stocker la clé décodée une fois

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository UserRepository;
    private final UserMapper UserMapper;

    public AuthService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserRepository UserRepository, UserMapper UserMapper) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.UserRepository = UserRepository;
        this.UserMapper = UserMapper;
    }
    
    // Méthode pour compatibilité
    public LoginResponse authenticateUser(@Valid LoginRequest authRequest) {
        return authenticateUser(authRequest, "unknown");
    }


    // Initialisation de la clé au démarrage du service
    // @PostConstruct est une bonne pratique pour l'initialisation après l'injection de dépendances
    @jakarta.annotation.PostConstruct // Utilisez jakarta.annotation.PostConstruct si Spring Boot 3+
    private void init() {
        // Décoder la chaîne Base64 en bytes, puis créer la SecretKey
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretString);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        // Vous pouvez vérifier la longueur ici pour vous assurer qu'elle est correcte
        System.out.println("Clé JWT chargée. Longueur (octets) : " + this.signingKey.getEncoded().length);
    }

    public LoginResponse authenticateUser(@Valid LoginRequest authRequest, String clientIp) {
        try {
            // Authentification
            System.out.println("Authentification en cours...");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
            // Récupération des détails de l'User
            User user = UserRepository.findByEmail(authRequest.getEmail()).orElseThrow(()-> new RessourceNotFoundException("user not found"));
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
            System.out.println("Authentification réussie pour l'User : " + userDetails.getUsername());
//            // Génération du token JWT
            String token = Jwts.builder()
                    .issuer("CLINIQUE-MANAGEMENT")
                    .subject(userDetails.getUsername())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // Expire dans 1 jour
                    .signWith(signingKey) // Utilisez la clé chargée et initialisée
                    .compact();

            // Construction de la réponse
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            loginResponse.setUser(UserMapper.userToUserResponseDTO(user));
            return loginResponse;

        } catch (Exception e) {
            // Gestion des erreurs avec un message explicite
            System.out.println("Erreur d'authentification : " + e.getMessage());
            throw new AuthenticationFailedException("Identifiants invalides : vérifiez l'e-mail ou le mot de passe.");
        }

    }
    public Roles getUserRole(LoginRequest loginRequest){
        User user = UserRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new RessourceNotFoundException("user not found"));
        return user.getRole();
    }
    
    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    
    public User getUserByEmail(String email) {
        return UserRepository.findByEmail(email)
                .orElseThrow(() -> new RessourceNotFoundException("User not found"));
    }



}