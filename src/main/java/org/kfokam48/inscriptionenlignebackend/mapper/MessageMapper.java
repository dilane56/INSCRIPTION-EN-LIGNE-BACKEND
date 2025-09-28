package org.kfokam48.inscriptionenlignebackend.mapper;


import org.kfokam48.inscriptionenlignebackend.dto.message.MessageDTO;
import org.kfokam48.inscriptionenlignebackend.dto.message.MessageResponseDTO;
import org.kfokam48.inscriptionenlignebackend.model.Message;
import org.kfokam48.inscriptionenlignebackend.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageMapper {
    private final UserRepository utilisateurRepository;

    public MessageMapper(UserRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }


    public MessageResponseDTO messageToMessageResponseDTO (Message message){
        MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
        messageResponseDTO.setExpediteurId(message.getExpediteur().getId());
        messageResponseDTO.setExpediteurNom(message.getExpediteur().getFirstName());
        messageResponseDTO.setExpediteurPrenom(message.getExpediteur().getLastName());
        messageResponseDTO.setDestinataireId(message.getDestinataire().getId());
        messageResponseDTO.setDestinataireNom(message.getDestinataire().getFirstName());
        messageResponseDTO.setDestinatairePrenom(message.getDestinataire().getLastName());
        messageResponseDTO.setStatus(message.getMessageStatus());
        messageResponseDTO.setId(message.getId());
        messageResponseDTO.setLu(message.getLu());
        messageResponseDTO.setContent(message.getContenu());
        messageResponseDTO.setDateEnvoi(message.getDateEnvoi());
        return messageResponseDTO;
    }

   public Message messageDToToMessage(MessageDTO messageDTO){
        Message message = new Message();
        message.setExpediteur(utilisateurRepository.findById(messageDTO.getExpediteurId()).orElseThrow(() -> new RuntimeException("Sender not found")));
        message.setDestinataire(utilisateurRepository.findById(messageDTO.getDestinataireId()).orElseThrow(() -> new RuntimeException("Receiver not found")));
        message.setContenu(messageDTO.getContenu());
        return message;
   }

   public List<MessageResponseDTO> messageListToMessageResponseDTOList(List<Message> messages) {
        return messages.stream()
                .map(this::messageToMessageResponseDTO)
                .toList();
    }

    public List<Message> messageDTOListToMessageList(List<MessageDTO> messageDTOs) {
        return messageDTOs.stream()
                .map(this::messageDToToMessage)
                .toList();
    }
}
