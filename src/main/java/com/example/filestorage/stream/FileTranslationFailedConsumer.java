package com.example.filestorage.stream;

import com.example.filestorage.entity.Status;
import com.example.filestorage.payload.MessageFileDto;
import com.example.filestorage.service.FileService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class FileTranslationFailedConsumer {
    private final FileService fileService;

    public FileTranslationFailedConsumer(FileService fileService) {
        this.fileService = fileService;
    }

    @Bean
    public Consumer<MessageFileDto> onFileTranslationFailed() {
        return (message) -> {
            fileService.updateStatus(message.getUserId(), message.getFileId(), Status.FAILED);
        };
    }
}
