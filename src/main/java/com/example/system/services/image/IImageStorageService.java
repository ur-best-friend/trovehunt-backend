package com.example.system.services.image;

import com.example.system.entity.User;
import org.springframework.lang.Nullable;

import java.io.IOException;

public interface IImageStorageService {
    String storeImageToURL(byte[] imgBytes, String filename, @Nullable User uploader) throws IOException;
    boolean isImageURLValid(String url);
}
