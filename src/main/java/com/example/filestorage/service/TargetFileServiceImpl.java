package com.example.filestorage.service;

import com.example.filestorage.entity.TargetFile;
import com.example.filestorage.entity.TargetFileType;
import com.example.filestorage.entity.File;
import com.example.filestorage.exception.FileNotFoundException;
import com.example.filestorage.repository.TargetFileRepository;
import com.example.filestorage.repository.FileRepository;
import org.springframework.stereotype.Service;

@Service
public class TargetFileServiceImpl implements TargetFileService {

    private String EXTRACTED = "extracted_";
    private String TRANSLATED = "translated_";
    private String PDF = "pdf";
    private String DOCX = "docx";

    private final FileRepository fileRepository;
    private final TargetFileRepository targetFileRepository;

    public TargetFileServiceImpl(FileRepository fileRepository, TargetFileRepository targetFileRepository) {
        this.fileRepository = fileRepository;
        this.targetFileRepository = targetFileRepository;
    }

    @Override
    public void addDownloadFile(String userId, long fileId, String uuid, TargetFileType type) {
        File file = fileRepository.findByIdAndUserId(fileId, userId)
                .orElseThrow(() -> new FileNotFoundException(userId, fileId));
        TargetFile targetFile = new TargetFile();

        String link = "";

        switch (type) {
            case EXTRACTED_DOCX -> link = EXTRACTED + uuid + "." + DOCX;
            case TRANSLATED_PDF -> link = TRANSLATED + uuid + "." + PDF;
            case TRANSLATED_DOCX -> link = TRANSLATED + uuid + "." + DOCX;
        }

        targetFile.setLink(link);
        targetFile.setFile(file);

        targetFileRepository.save(targetFile);
    }
}
