package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.Booking;
import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.entity.Vehicle;

import java.util.List;

public interface BookingDAO {

    /**
     * Make a new booking
     * @param user - user making the booking
     * @param vehicle - vehicle being booked
     * @return Booking made by the user
     */
    Booking create(User user, Vehicle vehicle);

    /**
     * Filter bookings made by the given user
     * @param user - whose booking is being filtered
     * @return List of bookings made by the user
     */
    List<Booking> getBookingByUser(User user);

    /**
     * Select the booking for the given bookingId
     * @param bookingId - id of booking
     * @return Matching booking
     */
    Booking getBookingById(int bookingId);

    /**
     * Return the vehicle and move the station where user left the vehicle off.
     * @param booking - booking that needs to be marked as stopped/returned
     * @param station - station where the vehicle is returned
     * @return Booking marked as returned
     */
    Booking returnVehicle(Booking booking, Station station);

}
