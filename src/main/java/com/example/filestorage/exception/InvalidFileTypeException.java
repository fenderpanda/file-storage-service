package com.example.filestorage.exception;

public class InvalidFileTypeException extends RuntimeException{

    public InvalidFileTypeException(String userId, String extension) {
        super(String.format("User '%s' could not upload file with extension '%s'", userId, extension));
    }
}
