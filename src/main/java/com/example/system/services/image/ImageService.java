package com.example.system.services.image;

import com.example.system.entity.User;
import com.example.system.entity.UserImage;
import com.example.system.exceptions.badrequest.ImageNotFoundException;
import com.example.system.exceptions.badrequest.InsufficientArgumentsException;
import com.example.system.exceptions.badrequest.InvalidFileException;
import com.example.system.exceptions.badrequest.InvalidImageException;
import com.example.system.repository.UserImageRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final UserImageRepository userImageRepository;
    private final IImageStorageService imageStorageService;

    public UserImage saveImage(MultipartFile multipartFile, User uploader) throws IOException {
        if(multipartFile==null) throw new InvalidFileException("File is null");
        return saveImage(multipartFile.getBytes(), multipartFile.getOriginalFilename(), uploader);
    }

    public UserImage saveImage(byte[] bytes, String filename, User uploader) throws IOException {
        if(bytes == null || bytes.length == 0) throw new InvalidFileException("File is null");
        try (InputStream input = new ByteArrayInputStream(bytes)) {
            try { ImageIO.read(input).toString(); }
            catch (Exception e) { throw new InvalidFileException("Invalid file type (image is required)"); }
        }
        String imageUrl = imageStorageService.storeImageToURL(bytes, filename, uploader);
        return userImageRepository.saveAndFlush(new UserImage(imageUrl,uploader));
    }

    public UserImage getImageByUrl(String url) {
        UserImage image = userImageRepository.findByUrl(url);
        if(image == null) throw new ImageNotFoundException(url+"");
        return image;
    }

    public boolean isImageURLValid(String url) {
        return !StringUtils.isEmpty(url) && imageStorageService.isImageURLValid(url);
    }

    public void checkForInvalidImageURLs(@NotNull List<String> urls) throws InvalidImageException {
        if (urls == null) throw new InsufficientArgumentsException("No image URL(s) were provided");
        checkForInvalidImageURLs(urls.toArray(new String[]{}));
    }

    public void checkForInvalidImageURLs(@NotNull String... urls) throws InvalidImageException {
        if (urls == null || urls.length == 0) throw new InsufficientArgumentsException("No image URL(s) were provided");
        for (String imgUrl: urls)
            if (!isImageURLValid(imgUrl)) throw new InvalidImageException(imgUrl);
    }
}
