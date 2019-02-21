package com.osetrova.documentservice.controller;


import com.osetrova.documentservice.exception.DirectoryCreationException;
import com.osetrova.documentservice.exception.DocumentNotFoundException;
import com.osetrova.documentservice.exception.DocumentSavingException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerHelper {

    @ExceptionHandler({DirectoryCreationException.class,
                        DocumentNotFoundException.class,
                        DocumentSavingException.class})
    public ResponseEntity<String> handleError(RuntimeException exception) {
        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(exception.getMessage());
    }
}
