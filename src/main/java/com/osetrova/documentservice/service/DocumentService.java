package com.osetrova.documentservice.service;

import com.osetrova.documentservice.config.UploadDirectoryConfig;
import com.osetrova.documentservice.converter.DocumentToDocumentSearchResultDtoConverter;
import com.osetrova.documentservice.dto.DocumentSearchParameter;
import com.osetrova.documentservice.dto.DocumentSearchResultDto;
import com.osetrova.documentservice.exception.DirectoryCreationException;
import com.osetrova.documentservice.exception.DocumentNotFoundException;
import com.osetrova.documentservice.exception.DocumentSavingException;
import com.osetrova.documentservice.model.Document;
import com.osetrova.documentservice.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DocumentService {

    private static final String EXTENSION = ".txt";
    private static final String UTC = "Europe/London";
    private DocumentRepository repository;
    private DocumentToDocumentSearchResultDtoConverter converter;
    private Path fileUploadDir;

    @Autowired
    public DocumentService(DocumentRepository repository,
                           DocumentToDocumentSearchResultDtoConverter converter,
                           UploadDirectoryConfig uploadDirectoryConfig) {
        this.repository = repository;
        this.converter = converter;
        this.fileUploadDir = Paths.get(uploadDirectoryConfig.getUploadDir())
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.fileUploadDir);
        } catch (Exception ex) {
            throw new DirectoryCreationException("Could not create the directory where the uploaded files will be stored.");
        }
    }

    public void upload(MultipartFile document, String type, String title) {
        try {
            Path targetLocation = this.fileUploadDir.resolve(title + EXTENSION);
            Files.copy(document.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            repository.save(Document.builder()
                                    .title(title)
                                    .type(type)
                                    .uploadDate(LocalDateTime.now(ZoneId.of(UTC)))
                                    .url(targetLocation.toString())
                                    .build());
        } catch (IOException e) {
            throw new DocumentSavingException("File saving failed! Try again.");
        }

    }

    public List<DocumentSearchResultDto> search(DocumentSearchParameter parameter) {
        return repository.searchBy(parameter).stream()
                .map(x -> converter.convert(x))
                .collect(Collectors.toList());
    }

    public Resource getById(Long id) {
        Document document = repository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document not find."));
        Resource resource;

        try {
            Path filePath = this.fileUploadDir.resolve(document.getUrl());
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new DocumentNotFoundException("Document not find.");
        }

        return resource;
    }
}
