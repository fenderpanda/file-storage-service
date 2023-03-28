package com.example.filestorage.service.fileinfo;

import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class ImageFile implements FileInfo{
    @Override
    public int getPageAmount(InputStream inputStream) {

        return 1;
    }
}
