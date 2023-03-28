package com.example.filestorage.exception;

public class InvalidBaseFilenameException extends RuntimeException{

    public InvalidBaseFilenameException(String userId, String filename) {
        super(String.format("User '%s' could not upload file '%s'", userId, filename));
    }
}
