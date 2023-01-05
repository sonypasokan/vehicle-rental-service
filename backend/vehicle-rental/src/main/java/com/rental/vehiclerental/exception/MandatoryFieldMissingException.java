package com.rental.vehiclerental.exception;

/**
 * Exception raised when the mandatory fields of the API are missing in the payload
 */
public class MandatoryFieldMissingException extends Exception {
    public MandatoryFieldMissingException(String field) {
        super(field + " is mandatory for this request.");
    }
}
