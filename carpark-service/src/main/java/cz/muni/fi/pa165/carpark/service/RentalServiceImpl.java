/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dao.RentalDao;
import cz.muni.fi.pa165.carpark.dto.RentalDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.exception.CarAlreadyReserved;
import cz.muni.fi.pa165.carpark.util.Converter;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * CRUD service for rental entity.
 *
 * @author Tomas Svoboda
 */
@Service
public class RentalServiceImpl implements RentalService
{
    @Autowired
    private RentalDao rentalDao;

    @Autowired
    private CarService carService;
    
    @PreAuthorize("!hasRole('ROLE_BUILT_IN_ADMIN')")
    @Transactional
    @Override
    public void create(RentalDto rental)
    {
        if(rental == null){
            throw new IllegalArgumentException("Rental is null");
        }
        
        if (!carService.getFreeCars(rental.getFromDate(), rental.getToDate()).contains(rental.getCar()))
        {
            throw new CarAlreadyReserved("car is already reserved.");
        }
        
        cz.muni.fi.pa165.carpark.entity.Rental entity = Converter.getEntity(rental);
        rentalDao.create(entity);
        rental.setId(entity.getId());
    }

    @PreAuthorize("hasAnyRole('ROLE_BUILT_IN_ADMIN', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    @Transactional
    @Override
    public void edit(RentalDto rental)
    {
        rentalDao.edit(Converter.getEntity(rental));
    }

    @PreAuthorize("hasAnyRole('ROLE_BUILT_IN_ADMIN', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    @Transactional
    @Override
    public void delete(RentalDto rental)
    {
        rentalDao.delete(Converter.getEntity(rental));
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    @Override
    public RentalDto get(Long id)
    {
        RentalDto rental = Converter.getTransferObject(rentalDao.get(id));

        return rental;
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    @Override
    public List<RentalDto> getAll()
    {
        List<RentalDto> rentals = new ArrayList<>();

        for (cz.muni.fi.pa165.carpark.entity.Rental rental : rentalDao.getAll())
        {
            rentals.add(Converter.getTransferObject(rental));
        }

        return rentals;
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    @Override
    public List<RentalDto> getAllByUser(UserDto user)
    {
        List<RentalDto> rentals = new ArrayList<>();

        for (cz.muni.fi.pa165.carpark.entity.Rental rental : rentalDao.getAllByUser(Converter.getEntity(user)))
        {
            rentals.add(Converter.getTransferObject(rental));
        }

        return rentals;
    }
}
