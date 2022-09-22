package com.example.system.services.image;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class Base64ImageConverter {
    public byte[] decodeValidateToBytes(String base64) throws IOException {
        byte[] imageByte = decodeToBytes(base64);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageByte)) {
            ImageIO.read(bis);
        }
        return imageByte;
    }
    public byte[] decodeToBytes(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}