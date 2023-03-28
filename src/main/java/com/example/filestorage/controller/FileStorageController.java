package com.example.filestorage.controller;

import com.example.filestorage.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class FileStorageController {

    private final FileStorageService fileStorageService;

    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Long> uploadFile(
            JwtAuthenticationToken token,
            MultipartFile file,
            @RequestParam String sourceLang,
            @RequestParam String targetLang) {

        String userId = token.getToken().getSubject();

        return ResponseEntity.ok(fileStorageService.upload(file, userId, sourceLang, targetLang));
    }

    @GetMapping("/file/{fileId}/download/{targetFileId}")
    public Resource downloadFile(
            JwtAuthenticationToken token,
            @PathVariable long fileId,
            @PathVariable long targetFileId) {

        String userId = token.getToken().getSubject();

        return fileStorageService.download(userId, fileId, targetFileId);
    }
}
