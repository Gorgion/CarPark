/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dto.CarDto;
import java.util.Collection;
import java.util.Date;

/**
 * CRUD operations on Car entity
 * 
 * @author Jiri Dockal
 */
public interface CarService
{
    public Long addCar(CarDto car);

	public CarDto getCar(Long id);

	public void editCar(CarDto car);

	public void deleteCar(CarDto car);

	public Collection<CarDto> getAllCars();

	public Collection<CarDto> getRentedCars();

	public Collection<CarDto> getFreeCars(Date from, Date to);
}
