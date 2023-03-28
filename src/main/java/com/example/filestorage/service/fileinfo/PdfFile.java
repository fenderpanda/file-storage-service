package com.example.filestorage.service.fileinfo;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class PdfFile implements FileInfo{
    @Override
    public int getPageAmount(InputStream inputStream) throws IOException {
        PDDocument document = Loader.loadPDF(inputStream);
        int pageAmount = document.getNumberOfPages();
        document.close();

        return pageAmount;
    }
}
