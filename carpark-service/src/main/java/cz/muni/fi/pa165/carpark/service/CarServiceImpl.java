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
import javax.transaction.Transactional;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import org.springframework.dao.DataAccessException;

/**
 * Service CRUD operations for carDto entity
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
        Car carEntity = Converter.getEntity(car);
        carDao.AddCar(carEntity);
    }

    @Transactional
    @Override
    public CarDto getCar(Long id)
    {
        Car carEntity = carDao.getCar(id);
        return Converter.getTransferObject(carEntity);
    }

    @Transactional
    @Override
    public void EditCar(CarDto car)
    {
        Car carEntity = Converter.getEntity(car);
        carDao.EditCar(carEntity);
    }

    @Transactional
    @Override
    public void DeleteCar(CarDto car)
    {
        Car carEntity = Converter.getEntity(car);
        carDao.DeleteCar(carEntity);
    }

    @Transactional
    @Override
    public Collection<CarDto> getAllCars()
    {
        List<CarDto> carsDto = new ArrayList<>();
        List<Car> cars = new ArrayList<>(carDao.getAllCars());
        for (Car car : cars)
        {
            carsDto.add(Converter.getTransferObject(car));
        }

        return Collections.unmodifiableCollection(carsDto);
    }

    @Transactional
    @Override
    public Collection<CarDto> getRentedCars()
    {
        List<CarDto> carsDto = new ArrayList<>();
        List<Car> cars = new ArrayList<>(carDao.getRentedCars());
        for (Car car : cars)
        {
            carsDto.add(Converter.getTransferObject(car));
        }

        return Collections.unmodifiableCollection(carsDto);
    }

    @Transactional
    @Override
    public Collection<CarDto> getFreeCars(Date from, Date to)
    {
        List<CarDto> carsDto = new ArrayList<>();
        List<Car> cars = new ArrayList<>(carDao.getFreeCars(from, to));
        for (Car car : cars)
        {
            carsDto.add(Converter.getTransferObject(car));
        }

        return Collections.unmodifiableCollection(carsDto);
    }
    
}
