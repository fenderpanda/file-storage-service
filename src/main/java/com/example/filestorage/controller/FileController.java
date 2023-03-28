package com.example.filestorage.controller;

import com.example.filestorage.entity.Status;
import com.example.filestorage.payload.FileResponse;
import com.example.filestorage.payload.MessageFileDto;
import com.example.filestorage.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/file")
    public FileResponse getAllFilesByUserId(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam(defaultValue = "updatedAt", required = false) String sortBy,
            @RequestParam(defaultValue = "desc", required = false) String sortDir,
            JwtAuthenticationToken token) {

        String userId = token.getToken().getSubject();

        return fileService.getAllFilesByUserId(userId, pageNo, pageSize, sortBy, sortDir);
    }

    @DeleteMapping("/file/{fileId}")
    public ResponseEntity<String> deleteFile(
            JwtAuthenticationToken token,
            @PathVariable long fileId
    ) {

        String userId = token.getToken().getSubject();

        fileService.markForDeletion(userId, fileId);

        return ResponseEntity.ok("Done");
    }

    @GetMapping("/file/{fileId}/status")
    public String getFileStatus(JwtAuthenticationToken token, @PathVariable long fileId) {

        String userId = token.getToken().getSubject();

        return fileService.getStatus(userId, fileId);
    }

    @GetMapping("/file/{fileId}/cost")
    public int getFileCost(JwtAuthenticationToken token, @PathVariable long fileId) {

        String userId = token.getToken().getSubject();

        return fileService.getFileCost(userId, fileId);
    }

    @GetMapping("/file/{fileId}/message")
    public MessageFileDto getMessageFileDto(JwtAuthenticationToken token, @PathVariable long fileId) {

        String userId = token.getToken().getSubject();

        return fileService.getMessageFileDto(userId, fileId);
    }

    @PutMapping("/file/{fileId}/status")
    public ResponseEntity<String> updateStatus(
            JwtAuthenticationToken token,
            @RequestParam Status status,
            @PathVariable long fileId) {

        String userId = token.getToken().getSubject();

        fileService.updateStatus(userId, fileId, status);

        return ResponseEntity.ok("DONE");
    }
}
