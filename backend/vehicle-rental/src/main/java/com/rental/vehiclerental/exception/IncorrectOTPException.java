package com.rental.vehiclerental.exception;

/**
 * Exception raised when the OTPs received are incorrect
 */
public class IncorrectOTPException extends Exception{
    public IncorrectOTPException(String message) {
        super(message);
    }
}
