package com.rental.vehiclerental.service;

import com.rental.vehiclerental.dao.PhoneDAO;
import com.rental.vehiclerental.dao.UserDAO;
import com.rental.vehiclerental.entity.PhoneOTP;
import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.exception.IncorrectOTPException;
import com.rental.vehiclerental.exception.UserNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.SplittableRandom;

@Service
public class AccountService implements AccountConnector {

    @Autowired
    private PhoneDAO phoneDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendOtp(String phone) {
        // Generate OTP
        String otp = generateOTP(4);
        System.out.println("OTP=" + otp);
        // send OTP
        sendOtp(phone, otp);
        // store OTP and phone number
        phoneDAO.save(phone, otp, 15);
    }

    private void sendOtp(String phone, String otp) {
        // TODO: Send OTP
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
        User user = userDAO.getUser(userId);
        if (user == null)
            throw new UserNotExistException("User with id " + userId + " doesn't exist");
        return userDAO.update(user, name, email);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User setAdmin(int userId) throws UserNotExistException {
        User user = userDAO.getUser(userId);
        if (user == null)
            throw new UserNotExistException("User with id " + userId + " doesn't exist");
        user = userDAO.setAdmin(user);
        return user;
    }
}
