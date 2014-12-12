/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dao.CarDao;
import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.exception.CarAlreadyExists;
import cz.muni.fi.pa165.carpark.exception.CarIsRented;
import cz.muni.fi.pa165.carpark.util.Converter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service CRUD operations for carDto entity
 * 
 * @author Jiri Dockal
 */
@Service
public class CarServiceImpl implements CarService
{
    @Autowired
    private CarDao carDao;
    
    @Transactional
    @Override
    public Long addCar(CarDto car)
    {
        if(car == null)
            throw new IllegalArgumentException("Car is null");
        
        Car carEntity = Converter.getEntity(car);
        
        if(!carDao.getIdByLicencePlate(carEntity.getLicencePlate()).isEmpty() || !carDao.getIdByVin(carEntity.getVIN()).isEmpty())
            throw new CarAlreadyExists("Car already exists!");

        return carDao.addCar(carEntity);
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
    public void editCar(CarDto car)
    {
        Car carEntity = Converter.getEntity(car);
        
        carDao.editCar(carEntity);
    }

    @Transactional
    @Override
    public void deleteCar(CarDto car)
    {
        Car carEntity = Converter.getEntity(car);
        if(carEntity.isRented())
            throw new CarIsRented("Car cannot be deleted when is rented");
        carDao.deleteCar(carEntity);
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
