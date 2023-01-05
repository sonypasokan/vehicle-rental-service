package com.rental.vehiclerental.exception;

/**
 * Exception raised when the non-admin user tries to perform an operation
 * which is restricted only to admin
 */
public class UserNotAdminException extends Exception{
    public UserNotAdminException(String message) {
        super(message);
    }
}
