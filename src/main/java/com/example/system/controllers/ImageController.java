package com.example.system.controllers;

import com.example.system.entity.UserImage;
import com.example.system.services.UserService;
import com.example.system.services.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final UserService userService;
    private final ImageService imageService;

    @Value("${upload.sizeLimit.bytes}")
    private long maxSize;

    @PostMapping("/upload")
    public UserImage uploadImage(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return imageService.saveImage(multipartFile, userService.getCurrentUser());
    }
}
