package com.rental.vehiclerental.service;

import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.exception.*;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public interface AccessEnabler {

    void sendOtp(String phone) throws MandatoryFieldMissingException, EnvironmentVariableMissingException;

    Pair<User, Boolean> validateOtp(String phone, String otp) throws IncorrectOTPException;

    User createProfile(int userId, String name, String email) throws UserNotExistException;

    User setAdmin(int userId) throws UserNotExistException;

    User verifyUser(int userId) throws UserNotExistException;

    User verifyAdminUser(int userId) throws UserNotExistException, UserNotAdminException;
}
