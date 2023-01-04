package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.entity.User;

import java.net.URL;
import java.util.List;

public interface StationDAO {

    Station add(User user, String stationName, URL location);

    Station getStation(int stationId);

    List<Station> getAllStations();

}
