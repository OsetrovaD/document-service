package com.osetrova.documentservice.repository;

import com.osetrova.documentservice.dto.DocumentSearchParameter;
import com.osetrova.documentservice.model.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentSearchRepository {

    List<Document> searchBy(DocumentSearchParameter parameter);
}
