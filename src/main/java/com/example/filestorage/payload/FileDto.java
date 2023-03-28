package com.example.filestorage.payload;

import com.example.filestorage.entity.Status;
import com.example.filestorage.entity.TargetFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class FileDto {

    private long id;

    private String filename;
    private String sourceLang;
    private String targetLang;

    private int pageAmount;
    private Status status;

    private Set<TargetFile> targetFiles;

    private LocalDateTime updatedAt;
}
