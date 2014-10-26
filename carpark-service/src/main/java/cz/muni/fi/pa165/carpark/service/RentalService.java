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

    void create(RentalDto rental);

    void delete(RentalDto rental);

    void edit(RentalDto rental);

    RentalDto get(Long id);

    List<RentalDto> getAll();

    List<RentalDto> getAllByUser(UserDto user);

}
