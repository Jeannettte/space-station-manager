package com.example.station.exception;

// Exception: malformed line, unreadable number, unknown module type
public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause){
        super(message, cause);
    }
}
