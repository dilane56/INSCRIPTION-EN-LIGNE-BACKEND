package org.kfokam48.inscriptionenlignebackend.service.auth;

import org.kfokam48.inscriptionenlignebackend.enums.Roles;
import org.kfokam48.inscriptionenlignebackend.model.Candidat;
import org.kfokam48.inscriptionenlignebackend.model.User;
import org.kfokam48.inscriptionenlignebackend.model.auth.CustomUserDetails;
import org.kfokam48.inscriptionenlignebackend.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    
    private final UserRepository userRepository;
    
    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oauth2User.getAttribute("sub");
        String email = oauth2User.getAttribute("email");
        String firstName = oauth2User.getAttribute("given_name");
        String lastName = oauth2User.getAttribute("family_name");
        String imageUrl = oauth2User.getAttribute("picture");
        Boolean emailVerified = oauth2User.getAttribute("email_verified");
        
        User user = processOAuth2User(provider, providerId, email, firstName, lastName, imageUrl, emailVerified);
        
        // Pour l'instant, retourner l'OAuth2User original pour que Spring Security fonctionne
        // Le SuccessHandler extraira l'email directement
        return oauth2User;
    }
    
    private synchronized User processOAuth2User(String provider, String providerId, String email, 
                                  String firstName, String lastName, String imageUrl, Boolean emailVerified) {
        
        Optional<User> userOptional = userRepository.findByEmail(email);
        
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            // Mettre à jour les infos OAuth2 si nécessaire
            if (!provider.equals(existingUser.getProvider())) {
                existingUser.setProvider(provider);
                existingUser.setProviderId(providerId);
                existingUser.setImageUrl(imageUrl);
                existingUser.setEmailVerified(emailVerified);
                return userRepository.save(existingUser);
            }
            return existingUser;
        } else {
            // Créer un nouveau candidat
            try {
                Candidat newCandidat = new Candidat();
                newCandidat.setFirstName(firstName != null ? firstName : "");
                newCandidat.setLastName(lastName != null ? lastName : "");
                newCandidat.setEmail(email);
                newCandidat.setProvider(provider);
                newCandidat.setProviderId(providerId);
                newCandidat.setImageUrl(imageUrl);
                newCandidat.setEmailVerified(emailVerified != null ? emailVerified : false);
                newCandidat.setRole(Roles.CANDIDAT);
                
                // Initialiser les champs obligatoires avec des valeurs vides
                newCandidat.setNationalite("");
                newCandidat.setTypeDePieceIdentite("");
                newCandidat.setNumeroPieceIdentite("");
                newCandidat.setAdresse("");
                newCandidat.setVille("");
                newCandidat.setCodePostal("");
                newCandidat.setPays("");
                newCandidat.setContactPourUrgence("");
                newCandidat.setTelephoneUrgence("");
                
                return userRepository.save(newCandidat);
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                // Contrainte unique violée - l'utilisateur a été créé par une autre requête
                // Récupérer l'utilisateur existant
                return userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur introuvable après création"));
            }
        }
    }
}