package com.example.system.exceptions.internal;

import com.example.system.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceParseException extends ApplicationException {
    public ResourceParseException() {
        super("Internal server exception, unable to parse JSON");
    }
}
