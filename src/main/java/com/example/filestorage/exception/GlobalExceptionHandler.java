package com.example.filestorage.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({SizeLimitExceededException.class})
    public ResponseEntity<String> sizeLimitExceededExceptionHandler(SizeLimitExceededException e) {

        log.error(e.getClass().getName(), e);

        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body("Uploaded file is bigger then 10Mb");
    }

    @ExceptionHandler({UploadFileException.class})
    public ResponseEntity<String> uploadFileExceptionHandler(UploadFileException e) {

        log.error(e.getClass().getName(), e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Server error");
    }

    @ExceptionHandler({DownloadFileException.class})
    public ResponseEntity<String> downloadFileExceptionHandler(DownloadFileException e) {

        log.error(e.getClass().getName(), e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Server error");
    }

    @ExceptionHandler({InvalidFileTypeException.class})
    public ResponseEntity<String> invalidFileTypeExceptionHandler(InvalidFileTypeException e) {

        log.error(e.getClass().getName(), e);

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body("Unsupported document type");
    }

    @ExceptionHandler({InvalidBaseFilenameException.class})
    public ResponseEntity<String> invalidBaseFilenameExceptionHandler(InvalidBaseFilenameException e) {

        log.error(e.getClass().getName(), e);

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body("Uploaded file name is empty");
    }

    @ExceptionHandler({FileNotFoundException.class})
    public ResponseEntity<String> fileNotFoundExceptionHandler(FileNotFoundException e) {

        log.error(e.getClass().getName(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("File not found");
    }

    @ExceptionHandler({TargetFileNotFoundException.class})
    public ResponseEntity<String> targetFileNotFoundExceptionHandler(TargetFileNotFoundException e) {

        log.error(e.getClass().getName(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("File not found");
    }
}
