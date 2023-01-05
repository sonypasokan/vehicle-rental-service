package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.exception.UserNotAdminException;
import com.rental.vehiclerental.exception.UserNotExistException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * DB operations on user entity
 */
@Repository
public class UserDAOImpl implements UserDAO{

    @Autowired
    private EntityManager entityManager;

    /**
     * Get user with matching id
     * @param userId - id of user
     * @return - User with matching id
     */
    private User getUser(int userId) {
        Session currentSession = entityManager.unwrap(Session.class);

        try {
            TypedQuery<User> query = currentSession.createQuery(
                    "select a from User a where a.id=:userId",
                    User.class
            );
            query.setParameter("userId", userId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Get user with matching phone.
     * @param phone - phone being searched
     * @return - User with matching phone.
     */
    @Override
    public User getUserByPhone(String phone) {
        Session currentSession = entityManager.unwrap(Session.class);

        try {
            TypedQuery<User> query = currentSession.createQuery(
                    "select a from User a where a.phone=:phone",
                    User.class
            );
            query.setParameter("phone", phone);
            query.setMaxResults(1);
            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    /**
     * Create a user with the given phone number
     * @param phone - phone no of user
     * @return User account created
     */
    @Override
    public User create(String phone) {
        User user = new User();
        user.setPhone(phone);
        entityManager.persist(user);
        return user;
    }

    /**
     * Update user object with given data
     * @param user - user object being updated
     * @param name - name of the user
     * @param email - email id of the user
     * @return - user object which is updated
     */
    @Override
    public User update(User user, String name, String email) {
        user.setName(name);
        user.setEmail(email);
        entityManager.persist(user);
        return user;
    }

    /**
     * Upgrade the given user with admin privileges
     * @param user - user that has to be upgraded
     * @return - Upgraded user
     */
    @Override
    public User setAdmin(User user) {
        user.setAdmin(true);
        entityManager.persist(user);
        return user;
    }

    /**
     * Verify if the given userId matches with a user
     * If so, return the user
     * @param userId - being searched for
     * @return - user object if the id matches
     * @throws UserNotExistException when id is not matched
     */
    @Override
    public User verifyUser(int userId) throws UserNotExistException {
        User user = getUser(userId);
        if (user == null)
            throw new UserNotExistException("User with id " + userId + " doesn't exist");
        return user;
    }

    /**
     * Verify if the given userId matches with a user
     * and has admin rights.
     * If so, return the user object.
     * @param userId - being searched for
     * @return user object if the id matches
     * @throws UserNotExistException when userId is wrong
     * @throws UserNotAdminException when the user is not an admin
     */
    @Override
    public User verifyAdminUser(int userId) throws UserNotExistException, UserNotAdminException {
        User user = verifyUser(userId);
        if (!user.isAdmin())
            throw new UserNotAdminException("User does not have admin privilege");
        return user;
    }
}
