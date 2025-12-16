package com.example.backend_project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Table
@Entity(name = "document")
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "file_name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "text", columnDefinition = "longtext")
    private String text;

    @Column(name = "metadata")
    private String metadata;

    @Column(name = "user", nullable = false)
    private Long user;

    @Column(name = "document")
    @Lob
    byte[] document;

    @Column(name = "error")
    private String error;

    @Column(name = "classification_type")
    private String classificationType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getClassificationType() {
        return classificationType;
    }

    public void setClassificationType(String classificationType) {
        this.classificationType = classificationType;
    }

    public Document() {
    }

    public Document(String name, String type, String text, String metadata, Long user, byte[] document, String error,
            String classificationType) {
        this.name = name;
        this.type = type;
        this.text = text;
        this.metadata = metadata;
        this.user = user;
        this.document = document;
        this.error = error;
        this.classificationType = classificationType;
    }

    public Document(Long id, String name, String type, String metadata, Long user) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.metadata = metadata;
        this.user = user;
    }

    public Document(Long id, String name, String type, Long user, String classificationType) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.user = user;
        this.classificationType = classificationType;
    }

    public Document(String text, String metadata, String name) {
        this.name = name;
        this.text = text;
        this.metadata = metadata;
    }

    public Document(byte[] document, String name) {
        this.document = document;
        this.name = name;
    }

}
