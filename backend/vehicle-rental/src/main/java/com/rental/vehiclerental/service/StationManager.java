package com.rental.vehiclerental.service;

import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.exception.UserNotAdminException;
import com.rental.vehiclerental.exception.UserNotExistException;

import java.net.MalformedURLException;
import java.util.List;

public interface StationManager {

    /**
     * Add the station
     * @param userId User's id
     * @param stationName Name of station
     * @param location Map location of the station
     * @return Station which is added
     * @throws UserNotExistException when the user does not exist
     * @throws UserNotAdminException when the user is not an admin
     * @throws MalformedURLException when the location URL is found incorrect
     */
    Station add(int userId, String stationName, String location) throws UserNotExistException, UserNotAdminException, MalformedURLException;

    /**
     * Get all stations in the DB
     * @return List of all stations
     */
    List<Station> getAllStations();

}
