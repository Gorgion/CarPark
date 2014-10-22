/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dto.Rental;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import java.util.List;
import java.util.concurrent.Future;
import org.springframework.scheduling.annotation.Async;

/**
 * Create, update, retrieve and delete operations on Rental entity.
 *
 * @author Tomas Svoboda
 */
public interface RentalService
{

    @Async
    Future<Void> create(Rental rental);

    @Async
    Future<Void> delete(Rental rental);

    @Async
    Future<Void> edit(Rental rental);

    @Async
    Future<Rental> get(Long id);

    @Async
    Future<List<Rental>> getAll();

    @Async
    Future<List<Rental>> getAllByUser(UserDto user);
    
}
