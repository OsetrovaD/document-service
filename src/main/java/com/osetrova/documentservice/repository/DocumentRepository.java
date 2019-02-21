package com.osetrova.documentservice.repository;

import com.osetrova.documentservice.model.Document;
import org.springframework.data.repository.CrudRepository;

public interface DocumentRepository extends CrudRepository<Document, Long>, DocumentSearchRepository {
}
