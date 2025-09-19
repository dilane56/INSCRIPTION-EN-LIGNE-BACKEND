package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.workflow.WorkflowActionDTO;
import org.kfokam48.inscriptionenlignebackend.service.WorkflowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflow")
@CrossOrigin("*")
public class WorkflowController {
    
    private final WorkflowService workflowService;
    
    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }
    
    @PostMapping("/soumettre/{inscriptionId}")
    public ResponseEntity<String> soumettre(@PathVariable Long inscriptionId) {
        workflowService.soumettreInscription(inscriptionId);
        return ResponseEntity.ok("Inscription soumise avec succès");
    }
    
    @PostMapping("/valider")
    public ResponseEntity<String> valider(@RequestBody WorkflowActionDTO action) {
        workflowService.validerInscription(action.getInscriptionId(), action.getAdminId(), action.getCommentaire());
        return ResponseEntity.ok("Inscription validée avec succès");
    }
    
    @PostMapping("/rejeter")
    public ResponseEntity<String> rejeter(@RequestBody WorkflowActionDTO action) {
        workflowService.rejeterInscription(action.getInscriptionId(), action.getAdminId(), action.getCommentaire());
        return ResponseEntity.ok("Inscription rejetée");
    }
}