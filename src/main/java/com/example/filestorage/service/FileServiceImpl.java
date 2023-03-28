package com.example.filestorage.service;

import com.example.filestorage.entity.File;
import com.example.filestorage.entity.Status;
import com.example.filestorage.exception.FileNotFoundException;
import com.example.filestorage.payload.FileDto;
import com.example.filestorage.payload.FileResponse;
import com.example.filestorage.payload.MessageFileDto;
import com.example.filestorage.repository.FileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService{
    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileResponse getAllFilesByUserId(String userId, int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<File> files = fileRepository.findAllByUserIdAndDeletedFalse(userId, pageable);
        List<FileDto> content = files.stream().map(this::mapToDto).toList();

        FileResponse fileResponse = new FileResponse();
        fileResponse.setFiles(content);
        fileResponse.setPageNo(files.getNumber());
        fileResponse.setPageSize(files.getSize());
        fileResponse.setTotalElements(files.getTotalElements());
        fileResponse.setTotalPages(files.getTotalPages());
        fileResponse.setLast(files.isLast());

        return fileResponse;
    }

    @Override
    public MessageFileDto getMessageFileDto(String userId, long fileId) {
        File file = fileRepository.findByIdAndUserId(fileId, userId)
                .orElseThrow(() -> new FileNotFoundException(userId, fileId));

        return mapToMessageFileDto(file);
    }

    @Override
    public void updateStatus(String userId, long fileId, Status status) {
        fileRepository.updateStatus(fileId, userId, status);
    }

    @Override
    public String getStatus(String userId, long fileId) {
        return fileRepository.getStatus(fileId, userId);
    }

    @Override
    public int getFileCost(String userId, long fileId) {
        return fileRepository.getFileCost(fileId, userId);
    }

    @Override
    public void markForDeletion(String userId, long fileId) {
        fileRepository.markDeleted(fileId, userId);
    }

    private FileDto mapToDto(File file) {
        FileDto fileDto = new FileDto();

        fileDto.setId(file.getId());
        fileDto.setFilename(file.getFilename());
        fileDto.setSourceLang(file.getSourceLang());
        fileDto.setTargetLang(file.getTargetLang());
        fileDto.setPageAmount(file.getPageAmount());
        fileDto.setStatus(file.getStatus());
        fileDto.setTargetFiles(file.getTargetFiles());
        fileDto.setUpdatedAt(file.getUpdatedAt());

        return fileDto;
    }

    private MessageFileDto mapToMessageFileDto(File file) {
        MessageFileDto messageFileDto = new MessageFileDto();

        messageFileDto.setFileId(file.getId());
        messageFileDto.setUserId(file.getUserId());
        messageFileDto.setUuid(file.getUuid());
        messageFileDto.setExtension(file.getExtension());

        return messageFileDto;
    }
}
