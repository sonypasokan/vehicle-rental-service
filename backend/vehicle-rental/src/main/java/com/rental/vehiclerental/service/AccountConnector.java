package com.rental.vehiclerental.service;

import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.exception.IncorrectOTPException;
import com.rental.vehiclerental.exception.UserNotExistException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public interface AccountConnector {

    void sendOtp(String phone);

    Pair<User, Boolean> validateOtp(String phone, String otp) throws IncorrectOTPException;

    User createProfile(int userId, String name, String email) throws UserNotExistException;

    User setAdmin(int userId) throws UserNotExistException;
}
