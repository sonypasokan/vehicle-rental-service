package com.rental.vehiclerental.service;

import com.rental.vehiclerental.entity.Booking;
import com.rental.vehiclerental.exception.BookingNotExistException;
import com.rental.vehiclerental.exception.UserNotExistException;
import com.rental.vehiclerental.exception.VehicleNotExistException;

import java.util.List;

public interface Bookable {

    Booking book(int userId, int vehicleId) throws UserNotExistException, VehicleNotExistException;

    List<Booking> view(int userId) throws UserNotExistException;

    Booking returnVehicle(int bookingId) throws BookingNotExistException;
}