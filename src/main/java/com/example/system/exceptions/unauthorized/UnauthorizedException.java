package com.example.system.exceptions.unauthorized;

import com.example.system.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends ApplicationException {
    public UnauthorizedException(){
        super("User is unauthorized");
    }
}
