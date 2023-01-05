package com.rental.vehiclerental.exception;

/**
 * Exception raised when the required environment variables are not found in the System
 */
public class EnvironmentVariableMissingException extends Exception {
    public EnvironmentVariableMissingException(String field) {
        super(field + " is not set as an environment variable.");
    }
}
