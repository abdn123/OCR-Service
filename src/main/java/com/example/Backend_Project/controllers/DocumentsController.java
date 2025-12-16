package com.example.backend_project.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend_project.dto.DocumentResponseDto;
import com.example.backend_project.entities.Document;
import com.example.backend_project.services.DocumentsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequestMapping("/documents")
@RestController
public class DocumentsController {

    private final DocumentsService documentsService;

    public DocumentsController(DocumentsService documentsService) {
        this.documentsService = documentsService;
    }

    @PostMapping
    public ResponseEntity<DocumentResponseDto> fileToString(@RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                DocumentResponseDto response = documentsService.parseDoc(file);
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                return ResponseEntity.status(500).body(new DocumentResponseDto(e.getMessage()));
            }
        } else {
            return ResponseEntity.badRequest().body(new DocumentResponseDto("File is empty"));
        }
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        return ResponseEntity.ok(documentsService.getAllDocuments());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Document>> getDocumentHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(documentsService.getUserDocumentHistory(userId));
    }

    @GetMapping("/document/{id}")
    public ResponseEntity<Optional<Document>> getDocumentById(@PathVariable Long id) {
        return ResponseEntity.ok(documentsService.findById(id));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Optional<Document>> downloadDocument(@PathVariable Long id) {
        return ResponseEntity.ok(documentsService.downloadDocument(id));
    }
}
