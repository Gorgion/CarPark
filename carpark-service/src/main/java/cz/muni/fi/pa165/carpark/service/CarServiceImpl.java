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
import javax.inject.Inject;
import static org.aspectj.apache.bcel.Repository.instanceOf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    public Long AddCar(CarDto car)
    {
        if(car == null)
            throw new IllegalArgumentException("Car is null");
        
        Car carEntity = Converter.getEntity(car);
        
        if(carDao.getIdByLicencePlate(carEntity.getLicencePlate()) != null || carDao.getIdByVin(carEntity.getVIN()) != null)
            throw new CarAlreadyExists("Car already exists!");
        
        return carDao.AddCar(carEntity);
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
        
        if(carDao.getIdByLicencePlate(carEntity.getLicencePlate()) != null || carDao.getIdByVin(carEntity.getVIN()) != null)
            throw new CarAlreadyExists("Car already exists!");
        
        carDao.EditCar(carEntity);
    }

    @Transactional
    @Override
    public void DeleteCar(CarDto car)
    {
        Car carEntity = Converter.getEntity(car);
        if(carEntity.getRented())
            throw new CarIsRented("Car cannot be deleted when is rented");
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
