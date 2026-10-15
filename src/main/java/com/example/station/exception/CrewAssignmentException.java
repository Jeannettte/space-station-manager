package com.example.station.exception;

//Exception: assigning a member already occupied to a full or destroyed module
public class CrewAssignmentException extends RuntimeException {
    public CrewAssignmentException(String message) {

        super(message);
    }
}
