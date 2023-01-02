package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.Booking;
import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.entity.Vehicle;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class BookingDAOImpl implements BookingDAO{

    @Autowired
    private EntityManager entityManager;

    @Override
    public Booking create(User user, Vehicle vehicle) {
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setVehicle(vehicle);
        booking.setFromTime(LocalDateTime.now());
        entityManager.persist(booking);
        return booking;
    }

    @Override
    public List<Booking> getBookingByUser(int userId) {
        Session currentSession = entityManager.unwrap(Session.class);
        List<Booking> bookings;

        TypedQuery<Booking> query = currentSession.createQuery(
                "select a from Booking a where a.user.id=:userId",
                Booking.class
        );
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public Booking getBookingById(int bookingId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Booking booking;

        TypedQuery<Booking> query = currentSession.createQuery(
                "select a from Booking a where a.id=:bookingId",
                Booking.class
        );
        query.setParameter("bookingId", bookingId);
        return query.getSingleResult();
    }

    @Override
    public Booking returnVehicle(Booking booking) {
        Session currentSession = entityManager.unwrap(Session.class);
        booking.setToTime(LocalDateTime.now());
        booking.setReturned(true);
        // TODO: Think any other table modification required while returning
        entityManager.persist(booking);
        return booking;
    }
}
