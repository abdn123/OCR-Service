package com.example.backend_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend_project.entities.InvoiceExtractedFields;

public interface InvoiceExtractedFieldsRepository extends JpaRepository<InvoiceExtractedFields, Long> {

}
