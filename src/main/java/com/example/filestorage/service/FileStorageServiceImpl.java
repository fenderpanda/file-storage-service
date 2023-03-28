package com.example.filestorage.service;

import com.example.filestorage.entity.Status;
import com.example.filestorage.entity.TargetFile;
import com.example.filestorage.entity.File;
import com.example.filestorage.entity.FileType;
import com.example.filestorage.exception.*;
import com.example.filestorage.repository.TargetFileRepository;
import com.example.filestorage.repository.FileRepository;
import com.example.filestorage.service.fileinfo.FileInfoService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    @Value("${app.directory.uploads}")
    private String uploadDir;
    private final FileRepository fileRepository;
    private final TargetFileRepository targetFileRepository;
    private final FileInfoService fileInfoService;

    public FileStorageServiceImpl(FileRepository fileRepository,
                                  TargetFileRepository targetFileRepository,
                                  FileInfoService fileInfoService) {
        this.fileRepository = fileRepository;
        this.targetFileRepository = targetFileRepository;
        this.fileInfoService = fileInfoService;
    }

    @Override
    public long upload(MultipartFile uploadedFile, String userId, String sourceLang, String targetLang) {

        String filename = uploadedFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        String baseFilename = FilenameUtils.getBaseName(filename);
        String extension = FilenameUtils.getExtension(filename);

        long filesize = uploadedFile.getSize();

        if (!isValidBaseFilename(baseFilename)) {
            throw new InvalidBaseFilenameException(userId, filename);
        }

        if (!isValidFileType(extension)) {
            throw new InvalidFileTypeException(userId, extension);
        }

        File file = new File();

        try {
            Files.copy(uploadedFile.getInputStream(), Paths.get(uploadDir).resolve(uuid + "." + extension));
            file.setPageAmount(fileInfoService.getPageAmount(uploadedFile.getInputStream(), extension));
        } catch (Exception e) {
            throw new UploadFileException(userId, filename, e);
        }

        file.setUserId(userId);
        file.setFilename(filename);
        file.setUuid(uuid);
        file.setExtension(extension);
        file.setSourceLang(sourceLang);
        file.setTargetLang(targetLang);
        file.setStatus(Status.NEW);
        file.setFilesize(filesize);

        return fileRepository.save(file).getId();
    }

    @Override
    public Resource download(String userId, long fileId, long targetFileId) {

        File file = fileRepository.findByIdAndUserId(fileId, userId)
                .orElseThrow(() -> new FileNotFoundException(userId, fileId));

        TargetFile targetFile = targetFileRepository.findById(targetFileId)
                .orElseThrow(() -> new TargetFileNotFoundException(userId, targetFileId));

        if (targetFile.getFile().getId() != file.getId()) {
            throw new TargetFileNotFoundException(userId, targetFileId);
        }

        try {
            Path resourceFile = Paths.get(uploadDir).resolve(targetFile.getLink());
            Resource resource = new UrlResource(resourceFile.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new DownloadFileException(userId, fileId);
            }
        } catch (MalformedURLException e) {
            throw new DownloadFileException(userId, fileId, e);
        }
    }

    private boolean isValidBaseFilename(String filename) {
        if (filename == null) {
            return false;
        }

        return !filename.isEmpty();
    }

    private boolean isValidFileType(String extension) {
        if (extension == null) {
            return false;
        }

        return Arrays.stream(FileType.values())
                .anyMatch(type -> type.toString().equalsIgnoreCase(extension));
    }
}
