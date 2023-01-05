package com.rental.vehiclerental.exception;

/**
 * Exception raised when the booking does not exist in the DB
 */
public class BookingNotExistException extends Exception{
    public BookingNotExistException(String message) {
        super(message);
    }
}
