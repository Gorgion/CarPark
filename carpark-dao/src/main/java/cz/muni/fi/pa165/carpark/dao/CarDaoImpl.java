/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.Car;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jiri Dockal
 */
@Repository
public class CarDaoImpl implements CarDao
{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Long AddCar(Car car)
    {
        if(car == null)
        {
            throw new IllegalArgumentException("Car cannot be null!");
        }
        
        em.persist(car);
        //em.flush();
        return car.getID();
    }

    @Override
    public Car getCar(Long id)
    {
        if(id == null) {
            throw new IllegalArgumentException("ID is NULL");
        }
        if(id <= 0) {
            throw new IllegalArgumentException("ID is less than 0");
        }
        
        Car car = em.find(Car.class, id);

        return car;
    }

    @Override
    public void EditCar(Car car)
    {
        if(car == null)
            throw new IllegalArgumentException("Car cannot be null!");
        
        em.merge(car);
    }

    @Override
    public void DeleteCar(Car car)
    {
        if(car == null)
            throw new IllegalArgumentException("Car cannot be null!");
        
        em.remove(em.find(Car.class, car.getID()));
    }

    @Override
    public Collection getAllCars()
    {
        Query query = em.createQuery("FROM Car");
        List<Car> cars = query.getResultList();
        
        return Collections.unmodifiableCollection(cars);
    }

    @Override
    public Collection getRentedCars()
    {
        Query query = em.createQuery("FROM Car WHERE Rented =:rented").setParameter("rented", true);
        List<Car> rentedCars = query.getResultList();
            
        return Collections.unmodifiableCollection(rentedCars);
    }

    @Override
    public Collection getFreeCars(Date from, Date to)
    {
        if(from.after(to))
            throw new IllegalArgumentException("From date is after to date!");
                
        Query query = em.createQuery("SELECT c FROM Car c WHERE c NOT IN (SELECT DISTINCT r.car FROM Rental r WHERE "
                + "(:to >= r.fromDate AND :to <= r.toDate) OR "
                + "(:from >= r.fromDate AND :from <= r.toDate))"
                , Car.class).setParameter("from", from).setParameter("to", to);
        List<Car> freeCars = query.getResultList();
        
        return Collections.unmodifiableCollection(freeCars);
    }
    
    @Override
    public List<Long> getIdByVin(String VIN)
    {
        Query query = em.createQuery("SELECT c.id FROM Car c WHERE c.VIN = :VIN", Long.class).setParameter("VIN", VIN);
        
        return query.getResultList();
    }
    
    @Override
    public List<Long> getIdByLicencePlate(String licencePlate)
    {
        Query query = em.createQuery("SELECT c.id FROM Car c WHERE c.licencePlate = :licencePlate", Long.class).setParameter("licencePlate", licencePlate);

        return query.getResultList();
    }
}
