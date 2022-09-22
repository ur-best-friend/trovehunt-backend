package com.example.system.exceptions;
public class ApplicationException extends RuntimeException {
    public ApplicationException() {
        super();
    }
    public ApplicationException(String message) {
        super(message);
    }
}
