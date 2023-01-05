package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class VehicleDAOImpl implements VehicleDAO{

    @Autowired
    private EntityManager entityManager;

    @Override
    public Vehicle getVehicleByRegId(String regId) {
        Session currentSession = entityManager.unwrap(Session.class);

        try {
            TypedQuery<Vehicle> query = currentSession.createQuery(
                    "select a from Vehicle a where a.regId=:regId",
                    Vehicle.class
            );
            query.setParameter("regId", regId);
            query.setMaxResults(1);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Vehicle add(User user, String regId, String type, String model) {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegId(regId);
        vehicle.setType(type);
        vehicle.setModel(model);
        vehicle.setAvailable(true);
        vehicle.setCreator(user);
        vehicle.setCreatedTime(LocalDateTime.now());
        entityManager.persist(vehicle);
        return vehicle;
    }

    @Override
    public void updatePrice(Vehicle vehicle, double price, User user) {
        VehiclePrice vehiclePrice = new VehiclePrice();
        vehiclePrice.setVehicle(vehicle);
        vehiclePrice.setPricePerHour(price);
        vehiclePrice.setUpdatedTime(LocalDateTime.now());
        vehiclePrice.setUpdatedBy(user);
        entityManager.persist(vehiclePrice);
    }

    @Override
    public void updateStationHistory(Vehicle vehicle, Station station, User user) {
        VehicleStationHistory vehicleStationHistory = new VehicleStationHistory();
        vehicleStationHistory.setVehicle(vehicle);
        vehicleStationHistory.setStation(station);
        vehicleStationHistory.setUpdatedTime(LocalDateTime.now());
        vehicleStationHistory.setUpdatedBy(user);
        entityManager.persist(vehicleStationHistory);
    }

    @Override
    public List<Vehicle> getVehiclesByStationAndAvailability(Station station, boolean availableOnly) {
        Session currentSession = entityManager.unwrap(Session.class);

        StringBuilder queryString = new StringBuilder();
        queryString.append("select a from Vehicle a where a.station=:station ");
        if (availableOnly)
            queryString.append("and isAvailable=true");
        TypedQuery<Vehicle> query = currentSession.createQuery(
                queryString.toString(),
                Vehicle.class
        );
        query.setParameter("station", station);
        return query.getResultList();
    }

    @Override
    public void updateStation(Vehicle vehicle, Station station) {
        vehicle.setStation(station);
        entityManager.persist(vehicle);
    }

    @Override
    public void makeAvailable(Vehicle vehicle, boolean available) {
        vehicle.setAvailable(available);
        entityManager.persist(vehicle);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        Session currentSession = entityManager.unwrap(Session.class);

        TypedQuery<Vehicle> query = currentSession.createQuery(
                "select a from Vehicle a",
                Vehicle.class
        );
        return query.getResultList();
    }

}
