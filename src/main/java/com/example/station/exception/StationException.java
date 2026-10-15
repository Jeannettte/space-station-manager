package com.example.station.exception;

// Parent de toutes les erreurs métiers de la station
public class StationException extends RuntimeException{
    public StationException(String message){
        super(message);
    }
    public StationException(String message, Throwable cause){
        super(message, cause);
    }

}
