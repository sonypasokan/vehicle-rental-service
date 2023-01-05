package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.exception.UserNotAdminException;
import com.rental.vehiclerental.exception.UserNotExistException;

public interface UserDAO {

    /**
     * Get user with matching phone.
     * @param phone - phone being searched
     * @return - User with matching phone.
     */
    User getUserByPhone(String phone);

    /**
     * Create a user with the given phone number
     * @param phone - phone no of user
     * @return User account created
     */
    User create(String phone);

    /**
     * Update user object with given data
     * @param user - user object being updated
     * @param name - name of the user
     * @param email - email id of the user
     * @return - user object which is updated
     */
    User update(User user, String name, String email);

    /**
     * Upgrade the given user with admin privileges
     * @param user - user that has to be upgraded
     * @return - Upgraded user
     */
    User setAdmin(User user);

    /**
     * Verify if the given userId matches with a user
     * If so, return the user
     * @param userId - being searched for
     * @return - user object if the id matches
     * @throws UserNotExistException when id is not matched
     */
    User verifyUser(int userId) throws UserNotExistException;

    /**
     * Verify if the given userId matches with a user
     * and has admin rights.
     * If so, return the user object.
     * @param userId - being searched for
     * @return user object if the id matches
     * @throws UserNotExistException when userId is wrong
     * @throws UserNotAdminException when the user is not an admin
     */
    User verifyAdminUser(int userId) throws UserNotExistException, UserNotAdminException;
}
