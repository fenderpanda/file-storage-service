package com.example.filestorage.service;

import com.example.filestorage.entity.Status;
import com.example.filestorage.payload.FileResponse;
import com.example.filestorage.payload.MessageFileDto;

public interface FileService {
    FileResponse getAllFilesByUserId(String userId, int pageNo, int pageSize, String sortBy, String sortDir);
    MessageFileDto getMessageFileDto(String userId, long fileId);
    void updateStatus(String userId, long fileId, Status status);
    String getStatus(String userId, long fileId);
    int getFileCost(String userId, long fileId);
    void markForDeletion(String userId, long fileId);
}
