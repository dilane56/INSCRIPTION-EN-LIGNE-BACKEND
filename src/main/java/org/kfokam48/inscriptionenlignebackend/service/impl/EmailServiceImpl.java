package org.kfokam48.inscriptionenlignebackend.service.impl;

import org.kfokam48.inscriptionenlignebackend.model.Inscription;
import org.kfokam48.inscriptionenlignebackend.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    
    @Value("${app.mail.from}")
    private String fromEmail;
    
    @Value("${app.base-url}")
    private String baseUrl;
    
    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void envoyerEmailSoumission(Inscription inscription) {
        Context context = new Context();
        context.setVariable("candidatNom", inscription.getCandidat().getFirstName());
        context.setVariable("formationNom", inscription.getFormation().getNomFormation());
        context.setVariable("numeroInscription", inscription.getId());
        context.setVariable("baseUrl", baseUrl);
        
        String contenu = templateEngine.process("email/soumission", context);
        envoyerEmail(inscription.getCandidat().getEmail(), "Inscription soumise avec succès", contenu);
    }

    @Override
    public void envoyerEmailValidation(Inscription inscription) {
        Context context = new Context();
        context.setVariable("candidatNom", inscription.getCandidat().getFirstName());
        context.setVariable("formationNom", inscription.getFormation().getNomFormation());
        context.setVariable("baseUrl", baseUrl);
        
        String contenu = templateEngine.process("email/validation", context);
        envoyerEmail(inscription.getCandidat().getEmail(), "Inscription validée", contenu);
    }

    @Override
    public void envoyerEmailRejet(Inscription inscription) {
        Context context = new Context();
        context.setVariable("candidatNom", inscription.getCandidat().getFirstName());
        context.setVariable("formationNom", inscription.getFormation().getNomFormation());
        context.setVariable("commentaire", inscription.getCommentaireAdmin());
        context.setVariable("baseUrl", baseUrl);
        
        String contenu = templateEngine.process("email/rejet", context);
        envoyerEmail(inscription.getCandidat().getEmail(), "Inscription non retenue", contenu);
    }

    @Override
    public void envoyerEmailRappel(Inscription inscription) {
        Context context = new Context();
        context.setVariable("candidatNom", inscription.getCandidat().getFirstName());
        context.setVariable("etapeActuelle", inscription.getEtapeActuelle());
        context.setVariable("baseUrl", baseUrl);
        
        String contenu = templateEngine.process("email/rappel", context);
        envoyerEmail(inscription.getCandidat().getEmail(), "Complétez votre inscription", contenu);
    }

    @Override
    public void envoyerEmailBienvenue(String email, String nom) {
        Context context = new Context();
        context.setVariable("nom", nom);
        context.setVariable("baseUrl", baseUrl);
        
        String contenu = templateEngine.process("email/bienvenue", context);
        envoyerEmail(email, "Bienvenue sur notre plateforme", contenu);
    }
    
    private void envoyerEmail(String destinataire, String sujet, String contenu) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(destinataire);
            helper.setSubject(sujet);
            helper.setText(contenu, true);
            
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur envoi email", e);
        }
    }
}