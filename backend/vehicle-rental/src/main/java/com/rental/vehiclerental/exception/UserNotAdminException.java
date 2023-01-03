package com.rental.vehiclerental.exception;

public class UserNotAdminException extends Exception{
    public UserNotAdminException(String message) {
        super(message);
    }
}
