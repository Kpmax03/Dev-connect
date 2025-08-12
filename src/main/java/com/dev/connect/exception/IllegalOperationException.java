package com.dev.connect.exception;

public class IllegalOperationException extends RuntimeException {
    public IllegalOperationException(String message) {
        super(message);
    }
    public IllegalOperationException() {
        super("illegle operation !!!!");
    }
}
