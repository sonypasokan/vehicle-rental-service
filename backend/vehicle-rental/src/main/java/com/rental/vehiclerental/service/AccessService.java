package com.rental.vehiclerental.service;

import com.rental.vehiclerental.constants.OTPStrings;
import com.rental.vehiclerental.dao.PhoneDAO;
import com.rental.vehiclerental.dao.UserDAO;
import com.rental.vehiclerental.entity.PhoneOTP;
import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.SplittableRandom;

@Service
public class AccessService implements AccessEnabler {

    @Autowired
    private PhoneDAO phoneDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendOtp(String phone) throws EnvironmentVariableMissingException {
        // Generate OTP
        String otp = generateOTP(OTPStrings.LENGTH_OF_OTP);
        System.out.println("OTP=" + otp);
        // send OTP
        sendOtp(phone, otp);
        // store OTP and phone number
        phoneDAO.save(phone, otp, OTPStrings.TTL_IN_MINUTES);
    }

    private void sendOtp(String phone, String otp) throws EnvironmentVariableMissingException {
        String message = String.format(OTPStrings.OTP_MESSAGE, otp);
        String sid = new SMSService().send(message, phone);
        System.out.println("Message id: " + sid);
    }

    private String generateOTP(int lengthOfOTP) {
        StringBuilder generatedOTP = new StringBuilder();
        SplittableRandom splittableRandom = new SplittableRandom();

        for (int i = 0; i < lengthOfOTP; i++) {
            int randomNumber = splittableRandom.nextInt(1, 9);
            generatedOTP.append(randomNumber);
        }
        return generatedOTP.toString();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pair<User, Boolean> validateOtp(String phone, String inputOtp) throws IncorrectOTPException {
        PhoneOTP phoneOTP = phoneDAO.getLatestOtpByPhone(phone);
        if (!phoneOTP.getOtp().equals(inputOtp))
            throw new IncorrectOTPException("OTP does not match.");
        if (phoneOTP.getExpireTime().isBefore(LocalDateTime.now()))
            throw new IncorrectOTPException("OTP has expired.");
        User user = userDAO.getUserByPhone(phone);
        if (user != null)
            return Pair.of(user, true);
        user = userDAO.create(phone);
        return Pair.of(user, false);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createProfile(int userId, String name, String email) throws UserNotExistException {
        User user = userDAO.verifyUser(userId);
        return userDAO.update(user, name, email);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User setAdmin(int userId) throws UserNotExistException {
        User user = userDAO.verifyUser(userId);
        user = userDAO.setAdmin(user);
        return user;
    }

}
