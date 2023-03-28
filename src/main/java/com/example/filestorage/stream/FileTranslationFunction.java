package com.example.filestorage.stream;

import com.example.filestorage.entity.Status;
import com.example.filestorage.entity.TargetFileType;
import com.example.filestorage.payload.MessageFileDto;
import com.example.filestorage.service.FileService;
import com.example.filestorage.service.TargetFileService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class FileTranslationFunction {
    private final TargetFileService targetFileService;
    private final FileService fileService;

    public FileTranslationFunction(TargetFileService targetFileService,
                                   FileService fileService) {
        this.targetFileService = targetFileService;
        this.fileService = fileService;
    }

    @Bean
    public Function<MessageFileDto, MessageFileDto> onFileTranslated() {
        return (message) -> {
            targetFileService.addDownloadFile(
                    message.getUserId(),
                    message.getFileId(),
                    message.getUuid(),
                    TargetFileType.EXTRACTED_DOCX);

            fileService.updateStatus(message.getUserId(), message.getFileId(), Status.DONE);

            return message;
        };
    }
}
