package com.example.filestorage.exception;

public class UploadFileException extends RuntimeException{

    public UploadFileException(String userId, String filename, Throwable err) {
        super(String.format("Server could not copy uploaded file\nUser '%s'; File '%s'", userId, filename),
                err);
    }
}
