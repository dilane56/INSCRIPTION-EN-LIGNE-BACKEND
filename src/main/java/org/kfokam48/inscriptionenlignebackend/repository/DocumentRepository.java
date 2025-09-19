package org.kfokam48.inscriptionenlignebackend.repository;

import org.kfokam48.inscriptionenlignebackend.enums.TypeDocument;
import org.kfokam48.inscriptionenlignebackend.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByInscriptionId(Long inscriptionId);
    boolean existsByHashDocumentAndInscription_Candidat_Id(String hash, Long candidatId);
    List<Document> findByTypeDocumentAndInscriptionId(TypeDocument type, Long inscriptionId);
    List<Document> findByValideAndInscription_Statut(Boolean valide, org.kfokam48.inscriptionenlignebackend.enums.StatutInscription statut);
}
