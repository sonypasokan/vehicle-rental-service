package com.rental.vehiclerental.exception;

public class MandatoryFieldMissingException extends Exception {
    public MandatoryFieldMissingException(String field) {
        super(field + " is mandatory for this request.");
    }
}
