/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dao.CarDao;
import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.util.Converter;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jiri Dockal
 */
public class CarServiceImpl implements CarService
{

    private CarDao carDao;
    
    public void setCarDao(CarDao carDao)
    {
        this.carDao = carDao;
    }
    
    @Override
    public void AddCar(CarDto car)
    {
        try
        {
            Car carEntity = Converter.getEntity(car);
            carDao.AddCar(carEntity);
        }
        catch (Exception ex)
        {
            throw new DataAccessException("Error when adding a car.");
        }
    }

    @Override
    public CarDto getCar(Long id)
    {
        Car carEntity = carDao.getCar(id);
        return 
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
