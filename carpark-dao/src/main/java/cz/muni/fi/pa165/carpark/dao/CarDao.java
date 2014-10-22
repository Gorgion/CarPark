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
/**
 *
 * @author Jiri Dockal
 */
public interface CarDao
{
        public void setEmf(EntityManagerFactory emf);
    
        public void AddCar(Car car);

	public Car getCar(Long id);

	public void EditCar(Car car);

	public void DeleteCar(Car car);

	public Collection getAllCars();

	public Collection getRentedCars();

	public Collection getFreeCars(Date from, Date to);

}
