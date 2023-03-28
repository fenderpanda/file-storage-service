package com.example.filestorage.service.fileinfo;

import java.io.IOException;
import java.io.InputStream;

public interface FileInfo {
    int getPageAmount(InputStream inputStream) throws IOException;
}
