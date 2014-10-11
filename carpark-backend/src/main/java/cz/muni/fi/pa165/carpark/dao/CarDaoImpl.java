/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Rental;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Jiri Dockal
 */
public class CarDaoImpl implements CarDao
{
    private EntityManagerFactory emf;

    @Override
    public void setEmf(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    @Override
    public void AddCar(Car car)
    {
        if(car == null)
            throw new IllegalArgumentException("Car cannot be null!");
        
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(car);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Car getCar(Long id)
    {
        if(id == null) {
            throw new IllegalArgumentException("ID is NULL");
        }
        if(id < 0) {
            throw new IllegalArgumentException("ID is less than 0");
        }
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Car car = em.find(Car.class, id);

        em.getTransaction().commit();
        em.close();
        
        return car;
    }

    @Override
    public void EditCar(Car car)
    {
        if(car == null)
            throw new IllegalArgumentException("Car cannot be null!");
        
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(car);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void DeleteCar(Car car)
    {
        if(car == null)
            throw new IllegalArgumentException("Car cannot be null!");
        
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.remove(em.find(Car.class, car.getID()));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Collection getAllCars()
    {        
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createQuery("FROM Car");
        List<Car> cars = query.getResultList();
        
        em.close();
        
        return Collections.unmodifiableCollection(cars);
    }

    @Override
    public Collection getRentedCars()
    {
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createQuery("FROM Car WHERE Rented =:rented").setParameter("rented", true);
        List<Car> rentedCars = query.getResultList();
        
        em.close();
        
        return Collections.unmodifiableCollection(rentedCars);
    }

    @Override
    public Collection getFreeCars(Date from, Date to)
    {
        if(from.after(to))
            throw new IllegalArgumentException("From date is after to date!");
        
        EntityManager em = emf.createEntityManager();
        
        /*List<Car> allCars = new ArrayList<Car>(getAllCars());
        List<Car> freeCars = new ArrayList<Car>();
        
        for(Car car : allCars)
        {
            Rental rent = car.getRent();
            if(rent != null)
            {
                if(!(from.before(rent.getToDate()) && to.after(rent.getFromDate())))
                    freeCars.add(car);
            }
        }*/
                
        //TODO edit naive version
        Query query = em.createQuery("SELECT c FROM Car c WHERE c.id NOT IN (SELECT DISTINCT r.car FROM Rental r WHERE :from < r.toDate AND :to > r.fromDate)", Car.class).setParameter("from", from).setParameter("to", to);
        List<Car> freeCars = query.getResultList();
        
        em.close();
        
        return Collections.unmodifiableCollection(freeCars);
    }
}
