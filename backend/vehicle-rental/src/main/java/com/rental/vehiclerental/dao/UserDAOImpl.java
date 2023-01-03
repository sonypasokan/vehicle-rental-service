package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class UserDAOImpl implements UserDAO{

    @Autowired
    private EntityManager entityManager;

    @Override
    public User getUser(int userId) {
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

    @Override
    public User create(String phone) {
        User user = new User();
        user.setPhone(phone);
        entityManager.persist(user);
        return user;
    }

    @Override
    public User update(User user, String name, String email) {
        user.setName(name);
        user.setEmail(email);
        entityManager.persist(user);
        return user;
    }

    @Override
    public User setAdmin(User user) {
        user.setAdmin(true);
        entityManager.persist(user);
        return user;
    }
}
