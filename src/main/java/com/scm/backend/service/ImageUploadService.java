package com.scm.backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    String uploadFile(MultipartFile file);
}
