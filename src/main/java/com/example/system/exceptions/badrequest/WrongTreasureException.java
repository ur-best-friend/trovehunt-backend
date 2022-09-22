package com.example.system.exceptions.badrequest;

import com.example.system.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongTreasureException extends ApplicationException {
    public WrongTreasureException(String message) { super(message); }
}
