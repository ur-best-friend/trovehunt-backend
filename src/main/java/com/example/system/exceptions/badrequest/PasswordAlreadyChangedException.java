package com.example.system.exceptions.badrequest;

import com.example.system.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordAlreadyChangedException extends ApplicationException {
    public PasswordAlreadyChangedException() {
        super("Password was already changed");
    }
}
