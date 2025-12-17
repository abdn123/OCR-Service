package com.example.backend_project.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.util.LinkedHashMap;
import java.util.Map;

import com.example.backend_project.dto.DocumentResponseDto;
import com.example.backend_project.entities.Document;
import com.example.backend_project.entities.User;
import com.example.backend_project.repositories.DocumentRepository;

@Service
public class DocumentsService {

    private final DocumentRepository documentRepository;

    public DocumentsService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    String tesseractPath = System.getenv().getOrDefault(
            "TESSERACT_PATH",
            "/usr/bin");

    String tessdataPath = System.getenv().getOrDefault(
            "TESSDATA_PATH",
            "/usr/share/tessdata");

    private PDFParserConfig getPdfParserConfig() {
        PDFParserConfig pdfConfig = new PDFParserConfig();
        pdfConfig.setEnableAutoSpace(true);
        pdfConfig.setExtractInlineImages(true);
        pdfConfig.setExtractAcroFormContent(true);
        pdfConfig.setOcrStrategy(PDFParserConfig.OCR_STRATEGY.OCR_ONLY);
        return pdfConfig;
    }

    public DocumentResponseDto parseDoc(MultipartFile file) throws IOException, SAXException, TikaException {
        Document document;
        DocumentResponseDto doc = new DocumentResponseDto();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        byte[] fileBytes = file.getBytes();
        Tika tika = new Tika();
        ByteArrayInputStream detectStream = new ByteArrayInputStream(fileBytes);
        doc.setType(tika.detect(detectStream));
        BodyContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        AutoDetectParser parser = new AutoDetectParser();
        ByteArrayInputStream docStream = new ByteArrayInputStream(fileBytes);

        if (doc.getType().startsWith("image/") || doc.getType().equals("application/pdf")) {
            TesseractOCRConfig ocrConfig = new TesseractOCRConfig();
            ocrConfig.setTesseractPath(tesseractPath);
            ocrConfig.setTessdataPath(tessdataPath);
            ocrConfig.setLanguage("eng");
            context.set(TesseractOCRConfig.class, ocrConfig);
            context.set(org.apache.tika.parser.pdf.PDFParserConfig.class, getPdfParserConfig());
        }
        parser.parse(docStream, handler, metadata, context);
        doc.setText(handler.toString());
        doc.setMetaData(metadata.toString());

        document = new Document(file.getOriginalFilename(), doc.getType(), doc.getText(), doc.getMetaData(),
                currentUser.getId(), file.getBytes(), null, classifyDocument(doc.getText()));
        documentRepository.save(document);
        return doc;
    }

    public List<Document> getUserDocumentHistory(long user) {
        return documentRepository.findByUser(user);
    }

    public long getDocumentCount() {
        return documentRepository.count();
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public long countByUser(long user) {
        return documentRepository.countByUser(user);
    }

    public Optional<Document> findById(Long id) {
        return documentRepository.findById(id);
    }

    public Optional<Document> downloadDocument(Long id) {
        return documentRepository.findDocumentById(id);
    }

    private String classifyDocument(String text) {
        if (text == null || text.isEmpty()) {
            return "generic";
        }

        text = text.toLowerCase();

        Map<String, Integer> scores = new LinkedHashMap<>();
        scores.put("generic", 0);
        scores.put("invoice", 0);
        scores.put("agreement", 0);
        scores.put("statement", 0);

        // -------- INVOICE --------
        if (text.contains("invoice"))
            scores.compute("invoice", (k, v) -> v + 40);
        if (text.contains("bill"))
            scores.compute("invoice", (k, v) -> v + 25);
        if (text.contains("receipt"))
            scores.compute("invoice", (k, v) -> v + 20);
        if (text.contains("total"))
            scores.compute("invoice", (k, v) -> v + 10);
        if (text.contains("vat"))
            scores.compute("invoice", (k, v) -> v + 10);

        // -------- AGREEMENT --------
        if (text.contains("agreement"))
            scores.compute("agreement", (k, v) -> v + 40);
        if (text.contains("contract"))
            scores.compute("agreement", (k, v) -> v + 40);
        if (text.contains("clause"))
            scores.compute("agreement", (k, v) -> v + 15);
        if (text.contains("partnership"))
            scores.compute("agreement", (k, v) -> v + 15);
        if (text.contains("terms"))
            scores.compute("agreement", (k, v) -> v + 15);
        if (text.contains("parties"))
            scores.compute("agreement", (k, v) -> v + 15);

        // -------- STATEMENT --------
        if (text.contains("statement"))
            scores.compute("statement", (k, v) -> v + 40);
        if (text.contains("balance"))
            scores.compute("statement", (k, v) -> v + 20);
        if (text.contains("account"))
            scores.compute("statement", (k, v) -> v + 20);
        if (text.contains("period"))
            scores.compute("statement", (k, v) -> v + 10);

        // -------- SELECT HIGHEST --------
        String bestMatch = "generic";
        int maxScore = 0;

        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            if (entry.getValue() > maxScore) {
                maxScore = entry.getValue();
                bestMatch = entry.getKey();
            }
        }

        if (maxScore < 50) {
            return "generic";
        }

        return bestMatch;
    }
}
