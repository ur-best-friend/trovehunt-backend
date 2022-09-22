package com.example.system.exceptions.badrequest;

import com.example.system.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidImageException extends ApplicationException {
    public InvalidImageException(String imageUrl) {
        super("Invalid image url: "+imageUrl);
    }
}
