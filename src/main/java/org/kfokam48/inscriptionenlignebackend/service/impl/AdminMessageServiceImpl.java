package org.kfokam48.inscriptionenlignebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.kfokam48.inscriptionenlignebackend.dto.message.AdminMessageDTO;
import org.kfokam48.inscriptionenlignebackend.dto.message.AdminMessageResponseDTO;
import org.kfokam48.inscriptionenlignebackend.enums.MessageStatus;
import org.kfokam48.inscriptionenlignebackend.model.Candidat;
import org.kfokam48.inscriptionenlignebackend.model.Message;
import org.kfokam48.inscriptionenlignebackend.model.User;
import org.kfokam48.inscriptionenlignebackend.repository.CandidatRepository;
import org.kfokam48.inscriptionenlignebackend.repository.MessageRepository;
import org.kfokam48.inscriptionenlignebackend.repository.UserRepository;
import org.kfokam48.inscriptionenlignebackend.service.AdminMessageService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminMessageServiceImpl implements AdminMessageService {

    private final MessageRepository messageRepository;
    private final CandidatRepository candidatRepository;
    private final UserRepository userRepository;

    @Override
    public List<AdminMessageResponseDTO> getAllMessages() {
        List<Message> messages = messageRepository.findAll();
        return messages.stream()
                .map(this::convertToAdminResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdminMessageResponseDTO> getConversationWithCandidat(Long candidatId) {
        User admin = getCurrentAdmin();
        List<Message> messages = messageRepository.findConversation(admin.getId(), candidatId);
        return messages.stream()
                .map(this::convertToAdminResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AdminMessageResponseDTO sendMessageToCandidat(AdminMessageDTO messageDTO) {
        User admin = getCurrentAdmin();
        Candidat candidat = candidatRepository.findById(messageDTO.getCandidatId())
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé"));

        Message message = new Message();
        message.setExpediteur(admin);
        message.setDestinataire(candidat);
        message.setContenu(messageDTO.getObjet() + "\n\n" + messageDTO.getContenu());
        message.setDateEnvoi(Instant.now());
        message.setMessageStatus(MessageStatus.SENT);
        message.setLu(false);

        Message savedMessage = messageRepository.save(message);
        return convertToAdminResponseDTO(savedMessage);
    }

    @Override
    @Transactional
    public void markMessageAsRead(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message non trouvé"));
        message.setLu(true);
        message.setMessageStatus(MessageStatus.READ);
        messageRepository.save(message);
    }

    private User getCurrentAdmin() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin non trouvé"));
    }

    private AdminMessageResponseDTO convertToAdminResponseDTO(Message message) {
        AdminMessageResponseDTO dto = new AdminMessageResponseDTO();
        dto.setId(message.getId());
        
        // Déterminer si c'est un candidat
        User candidatUser = message.getExpediteur().getId().equals(getCurrentAdmin().getId()) 
                ? message.getDestinataire() : message.getExpediteur();
        Candidat candidat = candidatRepository.findById(candidatUser.getId()).orElse(null);
        
        if (candidat != null) {
            dto.setCandidatId(candidat.getId());
            dto.setCandidatNom(candidat.getFirstName());
            dto.setCandidatPrenom(candidat.getLastName());
            dto.setCandidatEmail(candidat.getEmail());
        }
        
        // Extraire objet et contenu du message
        String[] parts = message.getContenu().split("\n\n", 2);
        dto.setObjet(parts.length > 0 ? parts[0] : "");
        dto.setContenu(parts.length > 1 ? parts[1] : message.getContenu());
        
        dto.setTypeMessage("MANUEL");
        dto.setStatut(message.getLu() ? "LU" : "NON_LU");
        dto.setEnvoyeParAdmin(message.getExpediteur().getId().equals(getCurrentAdmin().getId()));
        dto.setDateEnvoi(LocalDateTime.ofInstant(message.getDateEnvoi(), ZoneId.systemDefault()));
        
        return dto;
    }
}