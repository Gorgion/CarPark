/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dto.Rental;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import java.util.List;

/**
 * Create, update, retrieve and delete operations on Rental entity.
 *
 * @author Tomas Svoboda
 */
public interface RentalService
{

    void create(Rental rental);

    void delete(Rental rental);

    void edit(Rental rental);

    Rental get(Long id);

    List<Rental> getAll();

    List<Rental> getAllByUser(UserDto user);

}
