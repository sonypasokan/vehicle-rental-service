package com.rental.vehiclerental.service;

import com.rental.vehiclerental.entity.Booking;
import com.rental.vehiclerental.exception.BookingNotExistException;
import com.rental.vehiclerental.exception.StationNotExistException;
import com.rental.vehiclerental.exception.UserNotExistException;
import com.rental.vehiclerental.exception.VehicleNotExistException;

import java.util.List;

public interface Bookable {

    /**
     * Make a booking
     * @param userId User making the booking
     * @param regId Registration id of the vehicle
     * @return Booking object if booking is successful
     * @throws UserNotExistException when the given user id does not exist
     * @throws VehicleNotExistException when the vehicl does not exist
     */
    Booking book(int userId, String regId) throws UserNotExistException, VehicleNotExistException;

    /**
     * View all bookings made by the user
     * @param userId User's id
     * @return List of all bookings made by the user
     * @throws UserNotExistException when the given user does not exist
     */
    List<Booking> view(int userId) throws UserNotExistException;

    /**
     * End the booking and return the vehicle
     * @param bookingId Booking id
     * @param stationId Station from where the booking was done
     * @return Booking object after ending the booking
     * @throws BookingNotExistException when the booking does not exist
     * @throws StationNotExistException when the station does not exist
     */
    Booking returnVehicle(int bookingId, int stationId) throws BookingNotExistException, StationNotExistException;
}
