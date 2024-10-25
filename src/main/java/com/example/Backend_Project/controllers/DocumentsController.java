package com.example.backend_project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend_project.dto.DocumentResponseDto;
import com.example.backend_project.services.DocumentsService;

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
                return ResponseEntity.status(500).body(new DocumentResponseDto("Error reading file"));
            }
        } else {
            return ResponseEntity.badRequest().body(new DocumentResponseDto("File is empty"));
        }
    }
}
