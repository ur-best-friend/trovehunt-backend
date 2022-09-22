package com.example.system.services.image;

import com.example.system.config.WebMvcConfig;
import com.example.system.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageFileStorageService implements IImageStorageService {
    private final WebMvcConfig webConfig;

    @Override
    public String storeImageToURL(byte[] imgBytes, String filename, User uploader) throws IOException {
        File serveImagesStorage = new File(webConfig.getConfigServeImagesDir());
        if (!serveImagesStorage.exists() || !serveImagesStorage.isDirectory())
            if (!serveImagesStorage.mkdirs())
                throw new IOException("Unable to create folder for storing user images");
        String storageFilename = System.currentTimeMillis()+"_"+filename;
        try (FileOutputStream fileOutputStream = new FileOutputStream(Paths.get(serveImagesStorage.getPath(), storageFilename).toString())) {
            fileOutputStream.write(imgBytes);
        }
        return storageFilename;
    }

    @Override
    public boolean isImageURLValid(String url) {
        return url.matches("^(?!www\\.|(?:http|ftp)s?://|[A-Za-z]:\\\\|//).*");
    }

    @Bean
    public Base64ImageConverter getBase64ImageConverter() {
        return new Base64ImageConverter();
    }
}
