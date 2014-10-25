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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.dao.DataAccessException;

/**
 * Service CRUD operations for rental entity
 * 
 * @author Jiri Dockal
 */
@Named
public class CarServiceImpl implements CarService
{
    @Inject
    private CarDao carDao;
    
    public void setCarDao(CarDao carDao)
    {
        this.carDao = carDao;
    }
    
    @Transactional
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
            throw new DataAccessException("Error when adding a car.",ex) {};
        }
    }

    @Transactional
    @Override
    public CarDto getCar(Long id)
    {
        try
        {
            Car carEntity = carDao.getCar(id);
            return Converter.getTransferObject(carEntity);
        }
        catch(Exception ex)
        {
            throw new DataAccessException("Error when getting a car.",ex) {};
        }
    }

    @Transactional
    @Override
    public void EditCar(CarDto car)
    {
        try
        {
            Car carEntity = Converter.getEntity(car);
            carDao.EditCar(carEntity);
        }
        catch(Exception ex)
        {
            throw new DataAccessException("Error when editing a car.",ex) {};
        }
    }

    @Transactional
    @Override
    public void DeleteCar(CarDto car)
    {
        try
        {
            Car carEntity = Converter.getEntity(car);
            carDao.DeleteCar(carEntity);
        }
        catch(Exception ex)
        {
            throw new DataAccessException("Error when deleting a car.",ex) {};
        }
    }

    @Transactional
    @Override
    public Collection getAllCars()
    {
        try
        {
            List<CarDto> carsDto = new ArrayList<CarDto>();
            List<Car> cars = new ArrayList<Car>(carDao.getAllCars());
            for (Car car : cars)
            {
                carsDto.add(Converter.getTransferObject(car));
            }
            
            return Collections.unmodifiableCollection(carsDto);
        }
        catch(Exception ex)
        {
            throw new DataAccessException("Error when editing a car.",ex) {};
        }
    }

    @Transactional
    @Override
    public Collection getRentedCars()
    {
        try
        {
            List<CarDto> carsDto = new ArrayList<CarDto>();
            List<Car> cars = new ArrayList<Car>(carDao.getRentedCars());
            for (Car car : cars)
            {
                carsDto.add(Converter.getTransferObject(car));
            }
            
            return Collections.unmodifiableCollection(carsDto);
        }
        catch(Exception ex)
        {
            throw new DataAccessException("Error when editing a car.",ex) {};
        }
    }

    @Transactional
    @Override
    public Collection getFreeCars(Date from, Date to)
    {
        try
        {
            List<CarDto> carsDto = new ArrayList<CarDto>();
            List<Car> cars = new ArrayList<Car>(carDao.getFreeCars(from, to));
            for (Car car : cars)
            {
                carsDto.add(Converter.getTransferObject(car));
            }
            
            return Collections.unmodifiableCollection(carsDto);
        }
        catch(Exception ex)
        {
            throw new DataAccessException("Error when editing a car.",ex) {};
        }
    }
    
}
