package com.example.station.exception;

// Exception: Parent of all the station's business errors
public class StationException extends RuntimeException{
    public StationException(String message){
        super(message);
    }
    public StationException(String message, Throwable cause){
        super(message, cause);
    }

}
