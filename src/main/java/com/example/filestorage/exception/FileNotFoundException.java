package com.example.filestorage.exception;

public class FileNotFoundException extends RuntimeException{
    public FileNotFoundException(String userId, long fileId) {
        super(String.format("User '%s'\nFile not found with id: '%s'", userId, fileId));
    }
}
