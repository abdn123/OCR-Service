package com.example.backend_project.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend_project.entities.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("SELECT new com.example.backend_project.entities.Document(d.id, d.name, d.type, d.user, d.classificationType) FROM document d WHERE d.user = :user")
    List<Document> findByUser(Long user);

    @Query("SELECT new com.example.backend_project.entities.Document(d.text, d.metadata, d.name) FROM document d WHERE d.id = :id")
    Optional<Document> findById(Long id);

    @Query("SELECT new com.example.backend_project.entities.Document(d.document, d.name) FROM document d WHERE d.id = :id")
    Optional<Document> findDocumentById(Long id);

    @Query("SELECT COUNT(*) FROM document d WHERE d.user = :user")
    long countByUser(Long user);

    List<Document> findAll();

    long count();
}
