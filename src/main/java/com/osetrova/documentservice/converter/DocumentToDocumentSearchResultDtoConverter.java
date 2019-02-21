package com.osetrova.documentservice.converter;

import com.osetrova.documentservice.dto.DocumentSearchResultDto;
import com.osetrova.documentservice.model.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentToDocumentSearchResultDtoConverter implements Converter<Document, DocumentSearchResultDto> {

    @Override
    public DocumentSearchResultDto convert(Document document) {
        return DocumentSearchResultDto.builder()
                .id(document.getId())
                .title(document.getTitle())
                .type(document.getType())
                .build();
    }
}
