package com.example.filestorage.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    long upload(MultipartFile uploadedFile, String userId, String sourceLang, String targetLang);
    Resource download(String userId, long fileId, long targetFileId);
}
