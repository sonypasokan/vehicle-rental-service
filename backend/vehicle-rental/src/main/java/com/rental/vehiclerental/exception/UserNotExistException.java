package com.rental.vehiclerental.exception;

/**
 * Exception raised when the user does not exist in the DB
 */
public class UserNotExistException extends Exception{
    public UserNotExistException(String message) {
        super(message);
    }
}
