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
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * DB operations on Booking entity
 */
@Repository
public class BookingDAOImpl implements BookingDAO{

    @Autowired
    private EntityManager entityManager;

    /**
     * Make a new booking
     * @param user - user making the booking
     * @param vehicle - vehicle being booked
     * @return Booking made by the user
     */
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

    /**
     * Filter bookings made by the given user
     * @param user - whose booking is being filtered
     * @return List of bookings made by the user
     */
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

    /**
     * Select the booking for the given bookingId
     * @param bookingId - id of booking
     * @return Matching booking
     */
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

    /**
     * Return the vehicle and move the station where user left the vehicle off.
     * @param booking - booking that needs to be marked as stopped/returned
     * @param station - station where the vehicle is returned
     * @return Booking marked as returned
     */
    @Override
    public Booking returnVehicle(Booking booking, Station station) {
        LocalDateTime now = LocalDateTime.now();
        booking.setToTime(now);
        booking.setToStation(station);
        booking.setReturned(true);
        booking.setTotalAmount(calculatePrice(booking, now));
        entityManager.persist(booking);
        return booking;
    }

    /**
     * Calculate the amount for the ride
     * @param booking Booking for which amount is calculated
     * @param toTime Time at which booking ended
     * @return Total amount for the ride
     */
    private double calculatePrice(Booking booking, LocalDateTime toTime) {
        long hours = ChronoUnit.HOURS.between(toTime, booking.getFromTime());
        return hours * booking.getVehicle().getPricePerHour();
    }

}
