/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dao.RentalDao;
import cz.muni.fi.pa165.carpark.dto.RentalDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.util.Converter;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional
    @Override
    public void create(RentalDto rental)
    {
        cz.muni.fi.pa165.carpark.entity.Rental entity = Converter.getEntity(rental);
        rentalDao.create(entity);
        rental.setId(entity.getId());
    }

    @Transactional
    @Override
    public void edit(RentalDto rental)
    {
        rentalDao.edit(Converter.getEntity(rental));
    }

    @Transactional
    @Override
    public void delete(RentalDto rental)
    {
        rentalDao.delete(Converter.getEntity(rental));
    }

    @Transactional
    @Override
    public RentalDto get(Long id)
    {
        RentalDto rental = Converter.getTransferObject(rentalDao.get(id));

        return rental;
    }

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
