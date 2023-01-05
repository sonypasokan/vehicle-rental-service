package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.exception.UserNotAdminException;
import com.rental.vehiclerental.exception.UserNotExistException;

public interface UserDAO {

    User getUserByPhone(String phone);

    User create(String phone);

    User update(User user, String name, String email);

    User setAdmin(User user);

    User verifyUser(int userId) throws UserNotExistException;

    User verifyAdminUser(int userId) throws UserNotExistException, UserNotAdminException;
}
