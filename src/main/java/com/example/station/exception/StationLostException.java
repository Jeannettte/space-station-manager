package com.example.station.exception;

//Exception : If there's no more oxygen or no more crew: the game is lost
public class StationLostException extends RuntimeException {
    public StationLostException(String message) {
        super(message);
    }
}
