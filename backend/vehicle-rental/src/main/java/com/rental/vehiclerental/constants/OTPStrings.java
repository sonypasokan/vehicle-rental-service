package com.rental.vehiclerental.constants;

/**
 * Static strings configured throughout the project
 */
public abstract class OTPStrings {

    /**
     * Time to live in minutes.
     * This specifies for how long the OTP is considered valid.
     */
    public static final int TTL_IN_MINUTES = 10;

    /**
     * Length of the OTP.
     * This can be configured if we decide to send longer/shorter OTPs.
     */
    public static final int LENGTH_OF_OTP = 4;

    /**
     * Message which is sent to the user for passing on the OTP.
     */
    public static String OTP_MESSAGE =
            "Your OTP for logging into Rental Vehicle Service is %s";
}
