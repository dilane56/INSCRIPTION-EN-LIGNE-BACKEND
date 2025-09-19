package org.kfokam48.inscriptionenlignebackend.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kfokam48.inscriptionenlignebackend.enums.Roles;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    @Column(unique = true)
    private String email;
    private String phone;
    
    // OAuth2 fields
    private String provider; // "local", "google", "microsoft"
    private String providerId;
    private String imageUrl;
    private Boolean emailVerified = false;

    @Enumerated(EnumType.STRING)
    private Roles role; // Définition des rôles (Student, Teacher, Admin)


}
