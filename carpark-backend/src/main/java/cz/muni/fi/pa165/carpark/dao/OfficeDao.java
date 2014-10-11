/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Office;
import cz.muni.fi.pa165.carpark.entity.User;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 * Office entity with operations add, get, edit, delete.
 * It also finds all offices, office cars and office employees.
 *
 * @author Karolina Burska
 */
public interface OfficeDao { 
        public void setEMF(EntityManagerFactory emf);
        
        /**
         * Add new office
         * 
         * @param office new office to be added 
         */
        public void addOffice(Office office);

        /**
         * Get existing office
         * 
         * @param id id of existing office
         * @return office with the id specified
         */
	public Office getOffice(Long id);

        /**
         * Edit given office
         * 
         * @param office office for editing
         */
	public void editOffice(Office office);

        /**
         * Delete given office
         * 
         * @param office office for removal
         */
	public void deleteOffice(Office office);

        /**
         * Return all offices
         * 
         * @return List of all offices
         */
	public List<Office> getAllOffices();

        /**
         * Get all cars from specific office
         * 
         * @param office office from which cars are returned
         * @return List of all cars from one office
         */
	public List<Car> getOfficeCars(Office office);
        
        /**
         * Adds a new car to some office
         * 
         * @param office office into which a car should be added
         * @param car car for the office
         */
        public void addCarToOffice(Office office, Car car);

        /**
         * Deletes specified car from some office
         * 
         * @param office office from which a car is to be deleted
         * @param car car for deletion
         */
        public void deleteCarFromOffice(Office office, Car car);
        
        /**
         * Get all employees from specified office
         * 
         * @param office office from which employees are returned
         * @return List of all employees from one office
         */
	public List<User> getEmployees(Office office);
        
        /**
         * Adds specified user into some office
         * 
         * @param office office into which an user is to be added
         * @param user user for the office
         */
        public void addEmployeeToOffice(Office office, User user);
        
        /**
         * Deletes specified user from some office
         * 
         * @param office office from which an user is to be deleted
         * @param user user for deletion
         */
        public void deleteEmployeeFromOffice(Office office, User user);

}
