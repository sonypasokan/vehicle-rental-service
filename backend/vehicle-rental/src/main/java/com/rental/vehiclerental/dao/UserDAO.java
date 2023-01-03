package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.User;

public interface UserDAO {

    User getUser(int userId);

    User getUserByPhone(String phone);

    User create(String phone);

    User update(User user, String name, String email);

    User setAdmin(User user);
}
