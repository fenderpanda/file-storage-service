package com.example.filestorage.service;

import com.example.filestorage.entity.TargetFileType;

public interface TargetFileService {

    void addDownloadFile(String userId, long fileId, String uuid, TargetFileType type);
}
