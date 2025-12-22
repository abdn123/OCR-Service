package com.example.backend_project.dto;

public class ExtractedField {
    private String value;
    private int confidence;

    public ExtractedField(String value, int confidence) {
        this.value = value;
        this.confidence = confidence;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }
}
