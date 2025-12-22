package com.example.backend_project.dto;

public class InvoiceExtractionResult {
    private ExtractedField invoiceNumber;
    private ExtractedField invoiceDate;
    private ExtractedField totalAmount;
    private ExtractedField vendorName;

    public InvoiceExtractionResult(ExtractedField invoiceNumber, ExtractedField invoiceDate,
            ExtractedField totalAmount, ExtractedField vendorName) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.totalAmount = totalAmount;
        this.vendorName = vendorName;
    }

    public ExtractedField getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(ExtractedField invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public ExtractedField getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(ExtractedField invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public ExtractedField getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(ExtractedField totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ExtractedField getVendorName() {
        return vendorName;
    }

    public void setVendorName(ExtractedField vendorName) {
        this.vendorName = vendorName;
    }
}