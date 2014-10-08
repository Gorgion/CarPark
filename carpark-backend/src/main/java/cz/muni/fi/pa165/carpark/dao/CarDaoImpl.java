/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.Car;
import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void DeleteCar(Car car)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection getAllCars()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection getRentedCars()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection getFreeCars(Date from, Date to)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void checkCar(Car car)
    {
    }
}
