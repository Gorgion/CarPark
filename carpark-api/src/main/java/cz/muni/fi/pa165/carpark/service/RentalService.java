/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dto.RentalDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import java.util.List;

/**
 * Create, update, retrieve and delete operations on RentalDto entity.
 *
 * @author Tomas Svoboda
 */
public interface RentalService
{

    /**
     * Create new rental.
     *
     * @param rental rental for creation.
     */
    void create(RentalDto rental);

    /**
     * Remove rental object.
     *
     * @param rental rental for deletion.
     */
    void delete(RentalDto rental);

    /**
     * Update rental object.
     *
     * @param rental rental object
     */
    void edit(RentalDto rental);

    /**
     * Retrieve rental object with goven id.
     *
     * @param id id of the rental object
     * @return found rental
     */
    RentalDto get(Long id);

    /**
     * Retrieve all rentals
     *
     * @return all rentals
     */
    List<RentalDto> getAll();

    /**
     * Retrieve all rentals for given user.
     *
     * @param user user we want search all rentals for
     * @return rentals
     */
    List<RentalDto> getAllByUser(UserDto user);

}
