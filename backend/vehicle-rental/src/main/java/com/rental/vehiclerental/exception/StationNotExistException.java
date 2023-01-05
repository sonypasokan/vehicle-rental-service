package com.rental.vehiclerental.exception;

/**
 * Exception raised when the station does not exist in the DB
 */
public class StationNotExistException extends Exception{
    public StationNotExistException(String message) {
        super(message);
    }
}
