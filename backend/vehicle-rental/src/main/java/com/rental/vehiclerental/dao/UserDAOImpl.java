package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository
public class UserDAOImpl implements UserDAO{

    @Autowired
    private EntityManager entityManager;

    @Override
    public User getUser(int userId) {
        Session currentSession = entityManager.unwrap(Session.class);
        User user;

        TypedQuery<User> query = currentSession.createQuery(
                "select a from User a where a.id=:userId",
                User.class
        );
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }
}
