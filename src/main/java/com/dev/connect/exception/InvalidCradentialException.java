package com.dev.connect.exception;

public class InvalidCradentialException extends RuntimeException {
    public InvalidCradentialException(String message) {
        super(message);
    }
    public InvalidCradentialException() {
        super("access denied (Forbidden");
    }
}
