package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.entity.User;

import java.net.URL;
import java.util.List;

public interface StationDAO {

    /**
     * Add a new station.
     * @param user - admin user who is adding the station
     * @param stationName - name of the station
     * @param location - URL(map location) of the station
     * @return Station - which is added
     */
    Station add(User user, String stationName, URL location);

    /**
     * Get station matching the given id
     * @param stationId - id of station
     * @return Station which matches the id
     */
    Station getStation(int stationId);

    /**
     * Get all stations
     * @return list of all stations
     */
    List<Station> getAllStations();

}
