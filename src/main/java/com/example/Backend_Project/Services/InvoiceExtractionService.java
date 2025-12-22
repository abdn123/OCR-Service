package com.example.backend_project.services;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.springframework.stereotype.Service;
import com.example.backend_project.dto.ExtractedField;
import com.example.backend_project.dto.InvoiceExtractionResult;

@Service
public class InvoiceExtractionService {

    private static final Pattern INVOICE_NO_PATTERN = Pattern.compile(
            "(invoice\\s*(no|number|#)?\\s*[:\\-]?\\s*([A-Z0-9\\-]+))",
            Pattern.CASE_INSENSITIVE);

    public ExtractedField extractInvoiceNumber(String text) {
        Matcher matcher = INVOICE_NO_PATTERN.matcher(text);
        if (matcher.find()) {
            return new ExtractedField(matcher.group(3), 90);
        }
        return new ExtractedField(null, 0);
    }

    private static final Pattern DATE_PATTERN = Pattern.compile(
            "(\\b\\d{1,2}[/-]\\d{1,2}[/-]\\d{2,4}\\b|" +
                    "\\b\\d{1,2}-[A-Za-z]{3}-\\d{2,4}\\b|" +
                    "\\b[A-Za-z]{3}\\s\\d{1,2}\\s\\d{4}\\b)",
            Pattern.CASE_INSENSITIVE);

    public ExtractedField extractInvoiceDate(String text) {
        Matcher matcher = DATE_PATTERN.matcher(text);
        if (matcher.find()) {
            return new ExtractedField(matcher.group(), 85);
        }
        return new ExtractedField(null, 0);
    }

    public ExtractedField extractVendorName(String text) {
        String[] lines = text.split("\\n");

        for (int i = 0; i < Math.min(lines.length, 5); i++) {
            String line = lines[i].trim();
            if (line.length() > 5 &&
                    !line.toLowerCase().contains("invoice") &&
                    !line.toLowerCase().contains("tax")) {

                return new ExtractedField(line, 70);
            }
        }
        return new ExtractedField(null, 0);
    }

    private static final Pattern TOTAL_PATTERN = Pattern.compile(
            "(total|grand total|balance due)\\s*[:\\-]?\\s*(\\$|rs|pkr)?\\s*([0-9,]+\\.\\d{2})",
            Pattern.CASE_INSENSITIVE);

    public ExtractedField extractTotalAmount(String text) {
        Matcher matcher = TOTAL_PATTERN.matcher(text);
        if (matcher.find()) {
            return new ExtractedField(matcher.group(3), 95);
        }
        return new ExtractedField(null, 0);
    }

    public InvoiceExtractionResult extractInvoice(String text) {
        InvoiceExtractionResult result = new InvoiceExtractionResult(
                extractInvoiceNumber(text),
                extractInvoiceDate(text),
                extractTotalAmount(text),
                extractVendorName(text));
        return result;
    }
}
