package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;

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
    public void updateStation(Vehicle vehicle, Station station, User user) {
        VehicleStation vehicleStation = new VehicleStation();
        vehicleStation.setVehicle(vehicle);
        vehicleStation.setStation(station);
        vehicleStation.setUpdatedTime(LocalDateTime.now());
        vehicleStation.setUpdatedBy(user);
        entityManager.persist(vehicleStation);
    }

}
