package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DB operations on Station entity.
 */
@Repository
public class StationDAOImpl implements StationDAO{

    @Autowired
    private EntityManager entityManager;

    /**
     * Add a new station.
     * @param user - admin user who is adding the station
     * @param stationName - name of the station
     * @param location - URL(map location) of the station
     * @return Station - which is added
     */
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

    /**
     * Get station matching the given id
     * @param stationId - id of station
     * @return Station which matches the id
     */
    @Override
    public Station getStation(int stationId) {
        Session currentSession = entityManager.unwrap(Session.class);

        try {
            TypedQuery<Station> query = currentSession.createQuery(
                    "select a from Station a where a.id=:stationId",
                    Station.class
            );
            query.setParameter("stationId", stationId);
            query.setMaxResults(1);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Get all stations
     * @return list of all stations
     */
    @Override
    public List<Station> getAllStations() {
        Session currentSession = entityManager.unwrap(Session.class);
        TypedQuery<Station> query = currentSession.createQuery(
                "select a from Station a",
                Station.class
        );
        return query.getResultList();
    }
}
