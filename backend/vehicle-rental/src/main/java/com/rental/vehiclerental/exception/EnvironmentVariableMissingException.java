package com.rental.vehiclerental.exception;

public class EnvironmentVariableMissingException extends Exception {
    public EnvironmentVariableMissingException(String field) {
        super(field + " is not set as an environment variable.");
    }
}
