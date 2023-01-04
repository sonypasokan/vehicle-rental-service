package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.Booking;
import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.entity.Vehicle;

import java.util.List;

public interface BookingDAO {

    Booking create(User user, Vehicle vehicle);

    List<Booking> getBookingByUser(int userId);

    Booking getBookingById(int bookingId);

    Booking returnVehicle(Booking booking, Station station);
}
