package org.kfokam48.inscriptionenlignebackend.controller;

import lombok.RequiredArgsConstructor;
import org.kfokam48.inscriptionenlignebackend.dto.message.AdminMessageDTO;
import org.kfokam48.inscriptionenlignebackend.dto.message.AdminMessageResponseDTO;
import org.kfokam48.inscriptionenlignebackend.service.AdminMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/messages")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminMessageController {

    private final AdminMessageService adminMessageService;

    @GetMapping
    public ResponseEntity<List<AdminMessageResponseDTO>> getAllMessages() {
        return ResponseEntity.ok(adminMessageService.getAllMessages());
    }

    @GetMapping("/conversation/{candidatId}")
    public ResponseEntity<List<AdminMessageResponseDTO>> getConversation(@PathVariable Long candidatId) {
        return ResponseEntity.ok(adminMessageService.getConversationWithCandidat(candidatId));
    }

    @PostMapping("/send")
    public ResponseEntity<AdminMessageResponseDTO> sendMessage(@RequestBody AdminMessageDTO messageDTO) {
        return ResponseEntity.ok(adminMessageService.sendMessageToCandidat(messageDTO));
    }

    @PutMapping("/{messageId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long messageId) {
        adminMessageService.markMessageAsRead(messageId);
        return ResponseEntity.ok().build();
    }
}