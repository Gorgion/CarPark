/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.Car;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * CRUD operations on Car entity with additional getters
 *
 * @author Jiri Dockal
 */
public interface CarDao
{
        /**
         * Persists new car.
         * 
         * @param car 
         */
        public Long AddCar(Car car);

        /**
         * Return Car entity by given id.
         * 
         * @param id
         * @return car with given id
         */
	public Car getCar(Long id);

        /**
         * Edit car by given car entity.
         * 
         * @param car 
         */
	public void EditCar(Car car);

        /**
         * Delete car by given car entity.
         * 
         * @param car 
         */
	public void DeleteCar(Car car);

        /**
         * Return all Car entities.
         * 
         * @return collection of cars
         */
	public Collection getAllCars();

        /**
         * Return all cars with true variable rented.
         * 
         * @return collection of rented cars
         */
	public Collection getRentedCars();

        /**
         * Return all cars which are not rented in date given by parameters from and to.
         * 
         * @param from
         * @param to
         * @return collection of free cars
         */
	public Collection getFreeCars(Date from, Date to);

        /**
         * Return first found id by vin.
         * 
         * @param vin
         * @return id of car
         */
        public List<Long> getIdByVin(String vin);
    
        /**
         * Return first found id by licencePlate.
         * 
         * @param licencePlate
         * @return id of car
         */
        public List<Long> getIdByLicencePlate(String licencePlate);
}
