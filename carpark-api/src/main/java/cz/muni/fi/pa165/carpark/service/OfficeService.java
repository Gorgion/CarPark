/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import java.util.List;

/**
 *
 * @author Karolina Burska
 */
public interface OfficeService {
    /**
         * Add new office
         * 
         * @param office new office to be added 
         */
        public void addOffice(OfficeDto office);

        /**
         * Get existing office
         * 
         * @param id id of existing office
         * @return office with the id specified
         */
	public OfficeDto getOffice(Long id);

        /**
         * Edit given office
         * 
         * @param office office for editing
         */
	public void editOffice(OfficeDto office);

        /**
         * Delete given office
         * 
         * @param office office for removal
         */
	public void deleteOffice(OfficeDto office);

        /**
         * Return all offices
         * 
         * @return List of all offices
         */
	public List<OfficeDto> getAllOffices();

        /**
         * Get all cars from specific office
         * 
         * @param office office from which cars are returned
         * @return List of all cars from one office
         */
	public List<CarDto> getOfficeCars(OfficeDto office);
        
        /**
         * Adds a new car to some office
         * 
         * @param office office into which a car should be added
         * @param car car for the office
         */
        public void addCarToOffice(OfficeDto office, CarDto car);

        /**
         * Deletes specified car from some office
         * 
         * @param office office from which a car is to be deleted
         * @param car car for deletion
         */
        public void deleteCarFromOffice(OfficeDto office, CarDto car);
        
        /**
         * Get all employees from specified office
         * 
         * @param office office from which employees are returned
         * @return List of all employees from one office
         */
	public List<UserDto> getEmployees(OfficeDto office);
        
        /**
         * Adds specified user into some office
         * 
         * @param office office into which an user is to be added
         * @param user user for the office
         */
        public void addEmployeeToOffice(OfficeDto office, UserDto user);
        
        /**
         * Deletes specified user from some office
         * 
         * @param office office from which an user is to be deleted
         * @param user user for deletion
         */
        public void deleteEmployeeFromOffice(OfficeDto office, UserDto user);

}
