package com.example.station.exception;

//Exception: Make a faulty module work or repair it
public class ModuleOfflineException extends RuntimeException {
    public ModuleOfflineException(String message) {
        super(message);
    }
}
