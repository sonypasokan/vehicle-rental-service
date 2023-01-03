package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.net.URL;
import java.time.LocalDateTime;

@Repository
public class StationDAOImpl implements StationDAO{

    @Autowired
    private EntityManager entityManager;

    @Override
    public Station add(User user, String stationName, URL location) {
        Station station = new Station();
        station.setName(stationName);
        if (location != null)
            station.setLocation(location);
        station.setCreationTime(LocalDateTime.now());
        station.setCreator(user);
        entityManager.persist(station);
        return station;
    }
}
