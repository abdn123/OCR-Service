package com.example.backend_project.services;

import java.io.ByteArrayInputStream;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend_project.dto.DocumentResponseDto;

@Service
public class DocumentsService {

    private PDFParserConfig getPdfParserConfig() {
        PDFParserConfig pdfConfig = new PDFParserConfig();
        pdfConfig.setEnableAutoSpace(true);
        pdfConfig.setExtractInlineImages(true);
        pdfConfig.setExtractAcroFormContent(true);
        pdfConfig.setOcrStrategy(PDFParserConfig.OCR_STRATEGY.OCR_ONLY);
        return pdfConfig;
    }

    public DocumentResponseDto parseDoc(MultipartFile file) {

        DocumentResponseDto doc = new DocumentResponseDto();
        try {
            byte[] fileBytes = file.getBytes();
            Tika tika = new Tika();
            ByteArrayInputStream detectStream = new ByteArrayInputStream(fileBytes);
            doc.setType(tika.detect(detectStream));
            BodyContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();
            ParseContext context = new ParseContext();
            AutoDetectParser parser = new AutoDetectParser();
            ByteArrayInputStream docStream = new ByteArrayInputStream(fileBytes);
            
            if(doc.getType().startsWith("image/") || doc.getType().equals("application/pdf")) {
                TesseractOCRConfig ocrConfig = new TesseractOCRConfig();
                ocrConfig.setTesseractPath("C:/Program Files/Tesseract-OCR/");
                ocrConfig.setTessdataPath("C:/Program Files/Tesseract-OCR/tessdata");
                ocrConfig.setLanguage("eng");
                context.set(TesseractOCRConfig.class, ocrConfig);
                context.set(org.apache.tika.parser.pdf.PDFParserConfig.class, getPdfParserConfig());
            }
            parser.parse(docStream, handler, metadata, context);
            doc.setText(handler.toString());
            doc.setMetaData(metadata.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return doc;
    }

}
