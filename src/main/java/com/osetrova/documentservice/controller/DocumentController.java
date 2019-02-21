package com.osetrova.documentservice.controller;

import com.osetrova.documentservice.dto.DocumentSearchParameter;
import com.osetrova.documentservice.dto.DocumentSearchResultDto;
import com.osetrova.documentservice.service.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/document")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DocumentController {

    private DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<Void> upload(@RequestParam("document") MultipartFile document,
                                       @RequestParam("type") String type,
                                       @RequestParam("title") String title) {
        documentService.upload(document, type, title);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download(@PathVariable("id") Long id) {
        Resource document = documentService.getById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFilename() + "\"")
                .body(document);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<DocumentSearchResultDto>> search(@RequestBody DocumentSearchParameter searchParameter) {
        return ResponseEntity.ok(documentService.search(searchParameter));
    }
}
