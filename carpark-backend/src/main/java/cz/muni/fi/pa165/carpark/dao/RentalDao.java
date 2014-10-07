/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.Rental;
import cz.muni.fi.pa165.carpark.entity.User;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 * Create, retrieve and delete operations on Rental entity.
 *
 * @author Tomas Svoboda
 */
public interface RentalDao
{

    /**
     * Persist new rental
     * 
     * @param rental rental for persistance. 
     */
    void create(Rental rental);

    /**
     * Remove given rental.
     * 
     * @param rental rental for deletion
     */
    void delete(Rental rental);
    
    /**
     * Get rental by given id.
     * 
     * @param id id of searched rental
     * @return Searched rental
     */
    Rental get(Long id);

    /**
     *  Get all rentals
     * 
     * @return List of rentals.
     */
    List<Rental> getAll();

    /**
     * Get all rentals for a given user.
     * 
     * @param user user whose rentals we are searching
     * @return List of rentals.
     */
    List<Rental> getAllByUser(User user);

    /**
     * Setter for entity manager factory.
     * 
     * @param emf Given entity manager factory .
     */
    void setEmf(EntityManagerFactory emf);

}
