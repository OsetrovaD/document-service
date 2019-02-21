package com.osetrova.documentservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(
        exclude = {
                "title",
                "type",
                "uploadDate",
                "url"
        })
@Builder
@Entity
@Table(name = "document", schema = "document_service_storage")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @Column(name = "url", unique = true, nullable = false)
    private String url;
}
