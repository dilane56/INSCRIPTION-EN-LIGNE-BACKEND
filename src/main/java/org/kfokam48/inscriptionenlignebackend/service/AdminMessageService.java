package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.message.AdminMessageDTO;
import org.kfokam48.inscriptionenlignebackend.dto.message.AdminMessageResponseDTO;

import java.util.List;

public interface AdminMessageService {
    List<AdminMessageResponseDTO> getAllMessages();
    List<AdminMessageResponseDTO> getConversationWithCandidat(Long candidatId);
    AdminMessageResponseDTO sendMessageToCandidat(AdminMessageDTO messageDTO);
    void markMessageAsRead(Long messageId);
}