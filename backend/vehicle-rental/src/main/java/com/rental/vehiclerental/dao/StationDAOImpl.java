package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.entity.Vehicle;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

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
