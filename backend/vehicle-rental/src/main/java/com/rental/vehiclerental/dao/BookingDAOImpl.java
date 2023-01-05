package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.Booking;
import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.entity.Vehicle;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
        booking.setFromStation(vehicle.getStation());
        booking.setReturned(false);
        entityManager.persist(booking);
        return booking;
    }

    @Override
    public List<Booking> getBookingByUser(User user) {
        Session currentSession = entityManager.unwrap(Session.class);

        TypedQuery<Booking> query = currentSession.createQuery(
                "select a from Booking a where a.user=:user",
                Booking.class
        );
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public Booking getBookingById(int bookingId) {
        Session currentSession = entityManager.unwrap(Session.class);
        try {
            TypedQuery<Booking> query = currentSession.createQuery(
                    "select a from Booking a where a.id=:bookingId",
                    Booking.class
            );
            query.setParameter("bookingId", bookingId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Booking returnVehicle(Booking booking, Station station) {
        booking.setToTime(LocalDateTime.now());
        booking.setToStation(station);
        booking.setReturned(true);
        entityManager.persist(booking);
        return booking;
    }

}
