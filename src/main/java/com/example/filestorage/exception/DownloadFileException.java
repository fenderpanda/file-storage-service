package com.example.filestorage.exception;

public class DownloadFileException extends RuntimeException{

    public DownloadFileException(String userId, long fileId) {
        super(String.format("User with userId: '%s' could not download file: '%s'", userId, fileId));
    }

    public DownloadFileException(String userId, long fileId, Throwable err) {
        super(String.format("User with userId: '%s' could not download file: '%s'", userId, fileId),
                err);
    }
}
