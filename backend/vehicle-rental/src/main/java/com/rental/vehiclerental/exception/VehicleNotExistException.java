package com.rental.vehiclerental.exception;

/**
 * Exception raised when the vehicle does not exist in the DB
 */
public class VehicleNotExistException extends Exception{
    public VehicleNotExistException(String message) {
        super(message);
    }
}
