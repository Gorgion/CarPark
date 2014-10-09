/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.Office;
import java.util.Collection;
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
         * @return Collection of all offices
         */
	public Collection getAllOffices();

        /**
         * Get all cars from specific office
         * 
         * @param office office from which cars are returned
         * @return Collection of all cars from one office
         */
	public Collection getOfficeCars(Office office);

        /**
         * Get all employees from specified office
         * 
         * @param office office from which employees are returned
         * @return Collection on all employees from one office
         */
	public Collection getEmployees(Office office);

}
