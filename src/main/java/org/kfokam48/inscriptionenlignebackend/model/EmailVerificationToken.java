package org.kfokam48.inscriptionenlignebackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class EmailVerificationToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String token;
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private LocalDateTime expiryDate;
    
    public EmailVerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = LocalDateTime.now().plusHours(24);
    }
    
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}