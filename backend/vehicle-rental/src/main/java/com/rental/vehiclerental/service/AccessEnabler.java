package com.rental.vehiclerental.service;

import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.exception.*;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public interface AccessEnabler {

    /**
     * Send OTP when user wants to login to the application
     * @param phone user's phone number
     * @throws EnvironmentVariableMissingException when expected environment
     * variables are not set in the system
     */
    void sendOtp(String phone) throws MandatoryFieldMissingException, EnvironmentVariableMissingException;

    /**
     * Validate the OTP which user entered against the one sent from the application
     * @param phone phone to which OTP was sent
     * @param otp OTP input by the user
     * @return Pair of User object and a flag
     * User object will be present only if the validation was successful
     * Flag is to inform whether the user logged in for the first time or not
     * @throws IncorrectOTPException when the OTP entered does not match with what's sent to user
     */
    Pair<User, Boolean> validateOtp(String phone, String otp) throws IncorrectOTPException;

    /**
     * Create user's profile
     * @param userId user's id
     * @param name Name of the user
     * @param email Email id
     * @return User object which is created
     * @throws UserNotExistException when the given user id does not exist
     */
    User createProfile(int userId, String name, String email) throws UserNotExistException;

    /**
     * Make the given user an admin
     * @param userId User who has to be promoted as admin
     * @return User who is upgraded as admin
     * @throws UserNotExistException when the given user id does not exist
     */
    User setAdmin(int userId) throws UserNotExistException;

}
