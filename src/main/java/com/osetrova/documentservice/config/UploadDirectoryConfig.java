package com.osetrova.documentservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
@Data
public class UploadDirectoryConfig {

    private String uploadDir;
}
