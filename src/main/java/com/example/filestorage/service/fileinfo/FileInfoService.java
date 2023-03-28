package com.example.filestorage.service.fileinfo;

import com.example.filestorage.entity.FileType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileInfoService {
    private final Map<String, FileInfo> fileInfoMap;

    public FileInfoService(PdfFile pdfFile, DocxFile docxFile, ImageFile imageFile) {

        this.fileInfoMap = new HashMap<>();
        fileInfoMap.put(String.valueOf(FileType.PDF), pdfFile);
        //fileInfoMap.put(String.valueOf(FileType.DOCX), docxFile);
        //fileInfoMap.put(String.valueOf(FileType.JPEG), imageFile);
        //fileInfoMap.put(String.valueOf(FileType.JPG), imageFile);
    }

    public int getPageAmount(InputStream inputStream, String extension) throws IOException {
        FileInfo fileInfo = fileInfoMap.get(extension.toUpperCase());

        return fileInfo.getPageAmount(inputStream);
    }
}
