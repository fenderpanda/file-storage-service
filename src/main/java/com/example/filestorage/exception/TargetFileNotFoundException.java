package com.example.filestorage.exception;

public class TargetFileNotFoundException extends RuntimeException{

    public TargetFileNotFoundException(String userId, long targetFileId) {
        super(String.format("User '%s'\nTarget file not found with id: '%s'", userId, targetFileId));
    }
}
